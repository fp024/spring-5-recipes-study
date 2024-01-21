## 레시피 9-05-i 스프링  JDBC 프레임워크에서 예외 처리하기 - 1

>  스프링  JDBC 프레임워크에서 예외 처리하기 - 1
>

### 이번 레시피에서 확인해야할  내용

* ✅ 스프링  JDBC 프레임워크에서 예외 처리하기 - ID가 중복되는 상황을 만들어 예외 확인

  




## 진행

##### 레시피 9-05-i

* ID가 중복되는 상황을 만들어 예외 확인

  * MySQL의 경우 다음과 같다.

    ```
    org.springframework.dao.DuplicateKeyException: PreparedStatementCallback; SQL [INSERT INTO vehicle (color, wheel, seat, vehicle_no)
    VALUES (?, ?, ?, ?)
    ]; Duplicate entry 'EX0001' for key 'vehicle.PRIMARY'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry 'EX0001' for key 'vehicle.PRIMARY'
    ...
    ...
    java.sql.SQLIntegrityConstraintViolationException: Duplicate entry 'EX0001' for key 'vehicle.PRIMARY'
    	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:118) ~[mysql-connector-j-8.0.33.jar:8.0.33]
    ...
    ```

  * 검증은 다음과 같이 진행 (main() 에 중복을 발생시키는 코드가 있음)

    ```java
      @Test
      void testMain() {
        assertThatThrownBy(() -> Main.main(null)) //
            .isInstanceOf(DuplicateKeyException.class);
      }
    ```

    


## 의견

* ...



---

## 기타

####  log4jdbc 가 설정되어있어서, 중복로그가 나타나게 되는데..  Spring 예외로 변환되기 전의 예외가 `jdbc.sqltiming`의 ERROR 레벨 예외로 나타난다.

```
01:57:22.026 [main] ERROR jdbc.sqltiming -  com.zaxxer.hikari.pool.ProxyPreparedStatement.executeUpdate(ProxyPreparedStatement.java:61)
1. INSERT INTO vehicle (color, wheel, seat, vehicle_no)
VALUES ('Green', 4, 4, 'EX0001')
 {FAILED after 10 msec}
java.sql.SQLIntegrityConstraintViolationException: Duplicate entry 'EX0001' for key 'vehicle.PRIMARY'
	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:118) ~[mysql-connector-j-8.0.33.jar:8.0.33]
  ...
	at org.fp024.study.spring5recipes.vehicle.Main.run(Main.java:37) [main/:?]
	at org.fp024.study.spring5recipes.vehicle.Main.main(Main.java:45) [main/:?]
 ...
```

* Spring 의 예외로 감싸진 내용 (DuplicateKeyException 으로 감싸진 것이 보인다.)

  ```
  Exception in thread "main" org.springframework.dao.DuplicateKeyException: PreparedStatementCallback; SQL [INSERT INTO vehicle (color, wheel, seat, vehicle_no)
  VALUES (?, ?, ?, ?)
  ]; Duplicate entry 'EX0001' for key 'vehicle.PRIMARY'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry 'EX0001' for key 'vehicle.PRIMARY'
  	at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:244)
  	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:73)
  	at org.springframework.jdbc.core.JdbcTemplate.translateException(JdbcTemplate.java:1577)
  	at org.springframework.jdbc.core.JdbcTemplate.execute(JdbcTemplate.java:669)
  	at org.springframework.jdbc.core.JdbcTemplate.update(JdbcTemplate.java:962)
  	at org.springframework.jdbc.core.JdbcTemplate.update(JdbcTemplate.java:983)
  	at org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate.update(NamedParameterJdbcTemplate.java:331)
  	at org.fp024.study.spring5recipes.vehicle.dao.JdbcVehicleDao.insert(JdbcVehicleDao.java:73)
  	at org.fp024.study.spring5recipes.vehicle.Main.run(Main.java:37)
  	at org.fp024.study.spring5recipes.vehicle.Main.main(Main.java:45)
  Caused by: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry 'EX0001' for key 'vehicle.PRIMARY'
  	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:118)
  	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122)
  	at com.mysql.cj.jdbc.ClientPreparedStatement.executeInternal(ClientPreparedStatement.java:916)
  	at com.mysql.cj.jdbc.ClientPreparedStatement.executeUpdateInternal(ClientPreparedStatement.java:1061)
  	at com.mysql.cj.jdbc.ClientPreparedStatement.executeUpdateInternal(ClientPreparedStatement.java:1009)
  	at com.mysql.cj.jdbc.ClientPreparedStatement.executeLargeUpdate(ClientPreparedStatement.java:1320)
  	at com.mysql.cj.jdbc.ClientPreparedStatement.executeUpdate(ClientPreparedStatement.java:994)
  	at net.sf.log4jdbc.sql.jdbcapi.PreparedStatementSpy.executeUpdate(PreparedStatementSpy.java:1080)
  	at com.zaxxer.hikari.pool.ProxyPreparedStatement.executeUpdate(ProxyPreparedStatement.java:61)
  	at com.zaxxer.hikari.pool.HikariProxyPreparedStatement.executeUpdate(HikariProxyPreparedStatement.java)
  	at org.springframework.jdbc.core.JdbcTemplate.lambda$update$2(JdbcTemplate.java:967)
  	at org.springframework.jdbc.core.JdbcTemplate.execute(JdbcTemplate.java:650)
  	... 6 more
  ```

  




## 정오표

* p490쪽
  * 그림 9-1의 상속구조에서 `NestedRuntimeException` 가 두번 나왔는데, 두번째를 `DataAccessException` 로 바꿔야한다.
  


---

## JavaDocs

* ...



---

### 문서 사용 아이콘

* ✅
* ✔
* ⬜
* 💡
* ...

