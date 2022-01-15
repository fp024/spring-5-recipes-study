## 레시피 10-06-iii 트랜잭션 전달 속성 설정하기

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



* BookShopCashier.checkout() 에 `@Transactional(propagation = Propagation.REQUIRES_NEW)` 선언
* JdbcBookShop.purchase() 에도 `@Transactional(propagation = Propagation.REQUIRES_NEW)` 선언

purchase()에 REQUIRES_NEW로 지정했으므로 purchase()의 호출이 일단 성공했으면 commit되고 트랜젝션이 끝난다. 이후 다시 호출시 트랜젝션을 새로 만들어 호출하므로 이번엔 드디어 책한권은 사게됬음 😀

로그 상으로도 purchase() 메서드 호출 성공후에는 커밋 로그가 있음. (시작전에 checkout()에의해 생성된 최초 트랜젝션 일시 중단 로그가 있음)

```
21:25:23.824 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Switching JDBC Connection [HikariProxyConnection@209360767 wrapping org.hsqldb.jdbc.JDBCConnection@a44a291] to manual commit
21:25:23.825 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Suspending current transaction, creating new transaction with name [org.fp024.study.spring5recipes.bookshop.JdbcBookShop.purchase]

// 쿼리 실행

21:25:23.979 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Initiating transaction commit
21:25:23.979 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Committing JDBC transaction on Connection [HikariProxyConnection@485047320 wrapping org.hsqldb.jdbc.JDBCConnection@4834da9]
```

* 552쪽에 그림으로 흐름이 잘나와있다.

