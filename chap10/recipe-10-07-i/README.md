레시피 10-07-i 트랜잭션 격리 속성 설정하기

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



### READ_UNCOMMITED 격리 수준 테스트 예제

1. Thread 1로 0001 책의 재고를 5 증가 시킨다. (증가 후 10초 sleep후 롤백)
2. 5초 대기
3.  Thread 2로 0001 책의 재고를 확인한다 (확인후 10초 sleep : 재고 확인후 슬립은 별로 의미가 없어보이긴함 😓).

결과를 예상해보면 트랜젝션이 READ_UNCOMMITED로 시작된 상태에서  Thread 1은 재고 업데이트 후 아직 Sleep 중이고, 이때 Thread 2가 재고를 조회하므로 원래 재고 10에서 + 5되서 15개의 재고가 있다고 할 것임 그뒤 Sleep이 끝나서 롤백후에는 다시 10이 될 것임.



그런데... 예외가 발생해서, dataSource를 변경해보았다. HikariDataSource 에서 DriverManagerDataSource 으로 변경해서 에러는 안나는데, 동작을 보았을 대...

```
hread 1 - Prepare to increase book stock
Thread 1 - Book stock increased by 5
Thread 1 - Sleeping
Thread 2 - Prepare to check book stock  // 이미 체크할 수 있는 메서드에 들어온 상태인데...
Thread 1 - Wake up
Thread 1 - Book stock rolled back  // Thread 1에서 완전히 롤백이 완료된 후까지
Thread 2 - Book stock is 10        // 재고 조회 쿼리가 대기하다 읽어서 롤백된 결과를 읽게되어 10을 읽음.
Thread 2 - Sleeping
Thread 2 - Wake up
```

지금 HSQLDB여서그런 것 같은데...

이부분은 다른 DB에서도 확인해봐야할 것 같다.
