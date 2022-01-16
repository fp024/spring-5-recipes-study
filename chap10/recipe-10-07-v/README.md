## 레시피 10-07-v 트랜잭션 격리 속성 설정하기 - SERIALIZABLE

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



### SERIALIZABLE 예제

```
Thread 1 - Prepare to check book stock
Thread 1 - Book stock is 10
Thread 1 - Sleeping
Thread 2 - Prepare to increase book stock
Thread 1 - Wake up
Thread 2 - Book stock increased by 5
Thread 2 - Sleeping
Thread 2 - Wake up
Thread 2 - Book stock rolled back
```

예제 상으로 REPETABLE_READ 격리수준과 결과는 같다.

뭔가 재고 테이블에 대해 업데이트가 아닌 신규 행 INSERT가 있었다면 

~~REPETABLE_READ 에는 허용되더라도 SERIALIZABLE 에는 허용 안되었을 것 같은데...~~

INSERT 문을 넣긴했는데... REPETABLE_READ 와 SERIALIZABLE  결과는 동일했다.

```
Thread 1 - Prepare to check book stock
Thread 1 - Book stock is 10
Thread 1 - Sleeping
Thread 2 - Prepare to increase book stock
Thread 1 - Wake up
Thread 2 - 0003 stock inserted.   // 추가한 INSERT 문
Thread 2 - Book stock increased by 5
Thread 2 - Sleeping
Thread 2 - Wake up
Thread 2 - Book stock rolled back
```

지금까지 HSQLDB에서만 동작을 보아왔는데, 

Oracle 이나 MySQL에서 확인을 해보는 것이 좋을 것 같다.
