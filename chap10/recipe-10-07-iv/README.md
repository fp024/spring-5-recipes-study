## 레시피 10-07-iv 트랜잭션 격리 속성 설정하기 - REPEATABLE_READ 

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



### REPEATABLE_READ  예제

```
Thread 1 - Prepare to check book stock
Thread 1 - Book stock is 10
Thread 1 - Sleeping
Thread 2 - Prepare to increase book stock // 재고 증가 메서드안으로 진입은 했지만 재고 확인 트랜젝션의 완료를 기다리고 있다.
Thread 1 - Wake up						// 재고 확인 메서드 완료 이후...
Thread 2 - Book stock increased by 5	  // 재고 증가됨
Thread 2 - Sleeping
Thread 2 - Wake up
Thread 2 - Book stock rolled back
```

어떤 트랜젝션이 읽은 값을 다른 트랜젝션이 수정하지 못하게 차단. (왠지 ROW LOCK 인 것 같다.)

---

## 재확인

10-07-iii  와 로직은 같다 Thread 1이 조회 트랜젝션 중에 Thread 2가 재고를 늘림

checkStock() 에  `REPEATABLE_READ` 설정됨



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

책의 결과대로 수정차단을 하게하려면 조회 쿼리에 `FOR UPDATE`를 붙이지 않는 이상은 잠궈지지 않던데...

그런데 이부분이 DB에서 알아서 재정리를 할 수 있기 때문에 락을 최소화하려고 이렇게한 것인지? 잘 모르겠다.



```
Thread 1 - Prepare to check book stock
Thread 1 - Book stock is 10
Thread 1 - Sleeping
Thread 2 - Prepare to increase book stock
Thread 2 - Book stock increased by 5
Thread 2 - No Roolback            -- Thread 2가 재고를 업데이트하고 커밋 되었지만...
Thread 1 - Wake up
after sleep: Thread 1 - Book stock is 10   -- REPEATABLE_READ 트랜젝션 진행중인 Thread 1에서는 원래 값 10을 출력한다.
```

위에는 Thread 1 조회에서 sleep 이후에  한번더 조회결과를 출력해서 다른 스레드가 재고를 늘린 다음에 다시 조회를 해도 그것이 반영되진 않았다. 

REPEATABLE_READ로 시작한 checkStock()메서드의 트랜젝션을 끝내고 다시 읽어야 Thread 2가 업데이트한 내용을 읽을 수 있게 된다. 

한번 시작한 트랜젝션 내에서 원래 처음 읽었던 값을 계속 읽을 수 있는 것을 보장하는 모습이 보인다.

이것의 확인 때문에 코드가 바뀌어져 있는데.. 지금 바꿔놓은 것이 이해가 쉽다.



책대로 Thread 2가 대기하게 만들려면...

```java
 int stock =
        getJdbcTemplate()
            .queryForObject("SELECT STOCK FROM BOOK_STOCK WHERE ISBN = ? FOR UPDATE", Integer.class, isbn);
```

조회쿼리 끝에 FOR UPDATE를 붙여야한다.





### OracleXE 18c

```
Exception in thread "Thread 1" org.springframework.transaction.CannotCreateTransactionException: Could not open JDBC Connection for transaction; nested exception is java.sql.SQLException: READ_COMMITTED와 SERIALIZABLE만이 적합한 트랜잭션 레벨입니다
```

checkStock()의 조회 쿼리 실행할 때 발생



### HSQLDB 2.7.1

```
Thread 1 - Prepare to check book stock
Thread 1 - Book stock is 10
Thread 1 - Sleeping
Thread 2 - Prepare to increase book stock
Thread 1 - Wake up                      -- REPEATABLE_READ로 조회를 했던 Thread 1이 완료되어야...
Thread 2 - Book stock increased by 5    -- Thread 2가 수정 할 수 있었음.
Thread 2 - Sleeping
Thread 2 - Wake up
Thread 2 - Book stock rolled back

```

HSQLDB는 책의 내용과 동일하게 동작함.



새로 변경한 코드로는... 아래와 같음.

```
Thread 1 - Prepare to check book stock
Thread 1 - Book stock is 10
Thread 1 - Sleeping
Thread 2 - Prepare to increase book stock
Thread 1 - Wake up
after sleep: Thread 1 - Book stock is 10
Thread 2 - Book stock increased by 5
Thread 2 - No Rollback
```

HSQLDB에서는 조회 트랜젝션이 완료될 때까지 재고 업데이트 트랜젝션이 대기하므로 코드를 바꾸더라도 별 차이가 없긴하다. 😅

