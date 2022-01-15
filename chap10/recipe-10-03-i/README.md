## 레시피 10-03-i 트랜젝션 관리자 API를 이용해 프로그램 방식으로 트랜젝션 관리하기

* 10-01-i에서의 문제를 JDBC 커밋/롤백을 이용해 트랜젝션을 관리해본다.

* 스프링에서 제공하는 트랜젝션 관리자를 사용해 명시적 트랜젝션 관리

  ```java
    @Setter private PlatformTransactionManager transactionManager; // (1)
  
    public void purchase(String isbn, String username) {
      TransactionDefinition def = new DefaultTransactionDefinition();  // (2)
      TransactionStatus status = transactionManager.getTransaction(def);  // (3)
  
      try {
        // 쿼리 문;
        // ...
        transactionManager.commit(status);  // (4)
      } catch (DataAccessException e) {
        transactionManager.rollback(status); // (5)
        throw e;
      }
    }
  ```

  나는 보통 이런 방식을 명시적으로 들어왔는데, 책에서는 프로그램 방식이라고 한다.
