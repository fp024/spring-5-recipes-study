## 레시피 10-07-iii 트랜잭션 격리 속성 설정하기 - READ_UNCOMMITED 쿼리순서 바꿈

### 동시성 트랜잭션으로 발생할 수 있는 문제

* 오염된 값 읽기 (Dirty Read)
* 재현 불가한 읽기 (Nonrepeatable read)
* 허상 읽기 (Phantom read)
* 소실된 수정 (Lost updates)



### 스프링이 지원하는 트랜젝션 격리 수준

| 격리 수준       | 설명                                                         |
| --------------- | ------------------------------------------------------------ |
| DEFAULT         | DB 기본 격리 수준을 사용함, 대다수 DB는 READ_COMMITED가 기본 격리수준임. |
| READ_UNCOMMITED | 다른 트랜잭션이 아직 커밋하지 않은 값을 한 트랜잭션이 읽을 수 있음<br />따라서 오염된 값 읽기, 재현 불가한 읽기, 허상 읽기 문제는 여전히 남음. |
| READ_COMMITED   | 한 트랜잭션이 다른 트랜잭션이 커밋한 값만 읽을 수 있음.<br />오염된 값 읽기 문제는 해결되지만, 재현 불가한 읽기, 허상 읽기 문제는 여전히 남음. |
| REPEATABLE_READ | 트랜잭션이 어떤 필드를 여러번 읽어도 동일한 값을 읽도록 보장함.<br />트랜잭션이 지속되는 동안에는 다른 트랜잭션이 해당 필드를 변경할 수 없음.<br />오염된 값 읽기, 재현 불가한 읽기 문제는 해결되지만 허상 읽기 문제는 여전히 남음. |
| SERIALIZABLE    | 트랜잭션이 테이블을 여러번 읽어도 정확히 동일한 로우를 읽도록 보장함.<br />트랜잭션이 지속되는 동안에는 다른 트랜잭션이 해당 테이블에 삽입, 수정, 삭제를 할 수 없음.<br />동시성 문제는 모두 해소되지만 성능은 현저하게 떨어짐. |



### READ_UNCOMMITED 쿼리순서 바꾼 예제

이 예제는 재고 증가 전에 재고 확인를 먼저 했다.

그리고 재고 확인 위에 READ_UNCOMMITTED 를 붙였다.

그런데 뭔가 무슨의미가 있는 것인지 모르겠다...

```
Thread 1 - Prepare to check book stock
Thread 1 - Book stock is 10
Thread 1 - Sleeping
Thread 2 - Prepare to increase book stock  // 재고 증가 트랜젝션 끼어들어서 재고 증가를 정상적으로하고 
Thread 2 - Book stock increased by 5
Thread 2 - Sleeping 
Thread 1 - Wake up
Thread 2 - Wake up
Thread 2 - Book stock rolled back  // 롤백했다.
```

만약에 재고 확인 위해 READ_COMMITED라도 결과는 같았다.



## 재확인

이 예제는 로직이 좀 바뀐다.

* Thread 1에서 0001 책의 재고 조회
  1. 0001 재고 조회
  2. 재고 출력 (10일 텐데...)
  3. 10초간 Sleep
* 5초간 대기
* Thread 2에서 0001 책의 재고 5증가
  1. 재고 5 증가
  2. 10초간 Sleep.

그런데 이 예제는 여전히 무슨 의미가 있는지 잘 모르겠음... 😅

### MySQL 8.0.31

```
Thread 1 - Prepare to check book stock
Thread 1 - Book stock is 10
Thread 1 - Sleeping
Thread 2 - Prepare to increase book stock
Thread 2 - Book stock increased by 5
Thread 2 - Sleeping
Thread 1 - Wake up
Thread 2 - Wake up
Thread 2 - Book stock rolled back
```



### OracleXE 18c

```
Exception in thread "Thread 1" org.springframework.transaction.CannotCreateTransactionException: Could not open JDBC Connection for transaction; nested exception is java.sql.SQLException: READ_COMMITTED와 SERIALIZABLE만이 적합한 트랜잭션 레벨입니다
```

checkStock()의 SELECT를 실행할 시점에 예외가 발생



### HSQLDB 2.7.1

```
Thread 1 - Prepare to check book stock
Thread 1 - Book stock is 10
Thread 1 - Sleeping
Thread 2 - Prepare to increase book stock
Thread 2 - Book stock increased by 5
Thread 2 - Sleeping
Thread 1 - Wake up
Thread 2 - Wake up
Thread 2 - Book stock rolled back
```

MySQL의 동작과 차이가 없었다.

