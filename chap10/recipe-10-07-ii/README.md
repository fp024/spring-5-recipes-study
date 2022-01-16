## 레시피 10-07-ii 트랜잭션 격리 속성 설정하기 - READ_COMMITED

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



### READ_COMMITED 격리 수준 테스트 예제

checkStock에 `@Transactional(isolation = Isolation.READ_COMMITTED)`를 붙였는데, 트랜젝션이 롤백이 완료된 뒤에야 재고 조회가 되었다.

```
Thread 1 - Prepare to increase book stock
Thread 1 - Book stock increased by 5
Thread 1 - Sleeping
Thread 2 - Prepare to check book stock
Thread 1 - Wake up
Thread 1 - Book stock rolled back  // 재고 증가 트랜젝션 롤백이후에야
Thread 2 - Book stock is 10        // 재고 조회가능
Thread 2 - Sleeping
Thread 2 - Wake up
```

HSQLDB에서는 READ_UNCOMMITTED와 READ_COMMITTED의 동작이 

READ_COMMITTED 으로 같은 건가?

다른 DB에서도 동작을 보자...



저자님 예제의 리소스를 보니, 뭔가 다른 DB (더비)에서 테스트를 하셨는지? XML 설정이 몇몇 보인다.

난 그것 까진 포함할 필요는 없을 것 같다.
