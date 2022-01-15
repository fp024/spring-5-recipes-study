## 레시피 10-06-i 트랜잭션 전달 속성 설정하기

### 스프링에서 지원되는 트랜잭션 전달 방식

| 전달 속성     | 설명                                                         |
| ------------- | ------------------------------------------------------------ |
| REQUIRED      | 진행 중인 트랜잭션이 있으면 현재 메서드를 그 트랜잭션에서 실행하되 그렇지 않을 경우 새 트랜잭션을 시작하여 실행함. |
| REQUIRES_NEW  | 항상 새 트랜잭션을 시작해 현재 메서드를 실행하고 진행 중인 트랜잭션이 있으면 잠시 중단 시킴. |
| SUPPORTS      | 진행 중인 트랜잭션이 있으면 현재 메서드를 그 트랜잭션에서 실행하되, 그렇지 않을 경우 트랜잭션 없이 실행함. |
| NOT_SUPPORTED | 트랜잭션 없이 현재 메서드를 실행하고 진행 중인 트랜잭션이 있으면 잠시 중단 시킴. |
| MANDATORY     | 반드시 트랜잭션을 걸고 현재 메서드를 실행하되 진행 중인 트랜잭션이 없으면 예외를 던짐. |
| NEVER         | 반드시 트랜잭션 없이 현재 메서드를 실행하되 진행 중인 트랜잭션이 있으면 예외를 던짐. |
| NESTED        | 진행 중인 트랜잭션이 있으면 현재 메서드를 이 트랜잭션의 (JDBC 3.0 세이브포인트 기능이 있어야 가능한) 중첩 트랜잭션(nasted transaction) 내에서 실행함. 진행 중인 트랜잭션이 없으면 새 트랜잭션을 시작해서 실행함.<br /><br />백만개의 레코드를 만개씩 커밋하면서 진행한다고 할 때, 중간에 오류가 생겨도 중첩 트랜젝션을 롤백하면 만개 작업 분량만 롤백되어 배치 같은 작업에서 유용함. |



* 예제는 그리 특이하진 않은 것 같다.

  * BookShopCashier의 checkout에 @Transactional이 붙어있어서 여기서 트랜젝션이 시작해서 JdbcBookShop의 purchase위에 붙은 @Transactional은 그냥 전파 받은 진행중인 트랜젝션 그대로 사용하는 내용말고는 없는 것 같다.

    * propagation 의 기본값은 REQUIRED

    * 로그상으로도내용이보임.

      ```
      ...
      19:21:47.876 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Participating in existing transaction
      ...
      ... 쿼리 실패이후...
      19:21:47.983 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Participating transaction failed - marking existing transaction as rollback-only
      ```

* 책의 내용이 예제에 업데이트 안된 것 같은데...

  * 2개의 책이 있고, 첫번째 책은 구입할 수 있었지만 두번째 책을 구입할 수 없어 전부 실패하는 것을 보여주려한 것 같다.
  * 



