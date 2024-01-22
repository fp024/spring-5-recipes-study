## 레시피 9-05-iii 스프링  JDBC 프레임워크에서 예외 처리하기 - Spring 6 환경에서 테스트

>  스프링  JDBC 프레임워크에서 예외 처리하기 - 3
>

### 이번 레시피에서 확인해야할  내용

* ✔ 스프링  JDBC 프레임워크에서 예외 처리하기 - ID가 중복되는 상황을 만들어 예외 확인

* ✔ 스프링  JDBC 프레임워크에서 예외 처리하기 - MySQL 에러 코드 확인

* ✔ DataAccessException 예외를 커스텀 처리

* ✅ DataAccessException 예외를 커스텀 처리 - Spring 6 환경에서 테스트

  




## 진행

### 레시피 9-05-iii - Spring 6환경 테스트

* Spring JDBC 6.1.3의 `sql-error-codes.xml` 

  * https://github.com/spring-projects/spring-framework/blob/v6.1.3/spring-jdbc/src/main/resources/org/springframework/jdbc/support/sql-error-codes.xml
  
  
  

해당 DB의 duplicateKeyCodes를 errorCodes에 설정하고 실행할 때. `H2`만 제대로 되어서, Spring 6 환경에서 테스트를 진행해보았음.



#### H2 

* duplicateKeyCodes: `23001`,`23505`
* https://github.com/spring-projects/spring-framework/blob/b19f98bfd24bebbeaf5a5be44363e5484a0ff370/spring-jdbc/src/main/resources/org/springframework/jdbc/support/sql-error-codes.xml#L74

```
02:07:28.781 [main] INFO  org.fp024.study.spring5recipes.vehicle.Main - ### Error code: 23505
02:07:28.781 [main] INFO  org.fp024.study.spring5recipes.vehicle.Main - ### SQL state: 23505
Exception in thread "main" org.fp024.study.spring5recipes.vehicle.exception.MyDuplicateKeyException: PreparedStatementCallback: Unique index or primary key violation: "PUBLIC.PRIMARY_KEY_3 ON PUBLIC.VEHICLE(VEHICLE_NO) VALUES ( /* 3 */ 'EX0001' )"; SQL statement:
...
```

23505 코드를 설정했을 때는 정상적으로 MyDuplicateKeyException로 처리됨



#### MySQL 

* duplicateKeyCodes: `1062`
* https://github.com/spring-projects/spring-framework/blob/b19f98bfd24bebbeaf5a5be44363e5484a0ff370/spring-jdbc/src/main/resources/org/springframework/jdbc/support/sql-error-codes.xml#L200

```
02:08:24.034 [main] INFO  org.fp024.study.spring5recipes.vehicle.Main - ### Error code: 1062
02:08:24.034 [main] INFO  org.fp024.study.spring5recipes.vehicle.Main - ### SQL state: 23000
Exception in thread "main" org.springframework.dao.DuplicateKeyException: PreparedStatementCallback; SQL [INSERT INTO vehicle (color, wheel, seat, vehicle_no)
VALUES (?, ?, ?, ?)
...
```

😂: 1062로 설정해도 DataIntegrityViolationException로 처리됨.



### HSQLDB

* duplicateKeyCodes: `-104`
* https://github.com/spring-projects/spring-framework/blob/b19f98bfd24bebbeaf5a5be44363e5484a0ff370/spring-jdbc/src/main/resources/org/springframework/jdbc/support/sql-error-codes.xml#L137

```
02:29:27.281 [main] INFO  org.fp024.study.spring5recipes.vehicle.Main - ### Error code: -104
02:29:27.281 [main] INFO  org.fp024.study.spring5recipes.vehicle.Main - ### SQL state: 23505
Exception in thread "main" org.springframework.dao.DuplicateKeyException: PreparedStatementCallback; SQL [INSERT INTO vehicle (color, wheel, seat, vehicle_no)
VALUES (?, ?, ?, ?)
...
```

😂: -104로 설정해도 DataIntegrityViolationException로 처리됨.






## 의견

* **TODO**: Spring 6.1.3으로 버전업을 했을 때도 H2만 의도대로 동작함... 이건 문제가 확실하다고 판단되면 나중에 Spring Issue 에 문의해보자!
* Spring 6.1.3으로 버전업하면서, 뭔가 JdbcTemplate에서 Depreacted 경고가 나올까? 했는데.. 없었다..👍



---

## 기타

* ...




## 정오표

* ...
  


---

## JavaDocs

#### sql-error-codes.xml

```
  잘 알려진 데이터베이스의 기본 SQL 오류 코드입니다.
  클래스 경로 루트에 있는 "sql-error-codes.xml" 파일의 정의로 재정의될 수 있습니다.

  데이터베이스 제품 이름에 id 속성에 사용할 수 없는 문자(예: 공백)가 포함된 경우
  이 값을 보유하는 "databaseProductName"/"databaseProductNames"라는 속성을 추가해야 합니다.
  이 속성이 있으면 현재 데이터베이스를 기반으로 오류 코드를 조회하는 데 ID 대신 사용됩니다.
```





---

### 문서 사용 아이콘

* ✅
* ✔
* ⬜
* 💡
* ...

