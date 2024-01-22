## 레시피 9-05-iii 스프링  JDBC 프레임워크에서 예외 처리하기 - 3

>  스프링  JDBC 프레임워크에서 예외 처리하기 - 3
>

### 이번 레시피에서 확인해야할  내용

* ✔ 스프링  JDBC 프레임워크에서 예외 처리하기 - ID가 중복되는 상황을 만들어 예외 확인

* ✔ 스프링  JDBC 프레임워크에서 예외 처리하기 - MySQL 에러 코드 확인

* ✅ DataAccessException 예외를 커스텀 처리

  




## 진행

### 레시피 9-05-iii

* Spring JDBC 5.3.31의 `sql-error-codes.xml` 

  * https://github.com/spring-projects/spring-framework/blob/v5.3.31/spring-jdbc/src/main/resources/org/springframework/jdbc/support/sql-error-codes.xml
  
  
  

이번 레시피는 PostgreSQL의 중복 키 에러 코드 `23505`를  MyDuplicateKeyException에 매핑해서 사용도록 설정하는 것인데, 지금 나는 스프링 프로필 설정으로 MySQL, HSQLDB, H2를 번갈아가며 사용할 수 있지만,

기본을 MySQL로 정했으므로 `1062`를 에러코드로 설정해서 테스트를 하려고 했는데.. 잘 안된다.😅



해당 DB의 duplicateKeyCodes를 errorCodes에 설정하고 실행할 때. `H2`만 제대로 된다. 이유는 잘 모르겠음.😂

#### H2 

* duplicateKeyCodes: `23001`,`23505`
* https://github.com/spring-projects/spring-framework/blob/8c85c3166c4f9ee6b77862da2229e94162c7cc43/spring-jdbc/src/main/resources/org/springframework/jdbc/support/sql-error-codes.xml#L73

```
01:39:01.583 [main] INFO  org.fp024.study.spring5recipes.vehicle.Main - ### Error code: 23505
01:39:01.583 [main] INFO  org.fp024.study.spring5recipes.vehicle.Main - ### SQL state: 23505
Exception in thread "main" org.fp024.study.spring5recipes.vehicle.exception.MyDuplicateKeyException: PreparedStatementCallback: Unique index or primary key violation: "PUBLIC.PRIMARY_KEY_3 ON PUBLIC.VEHICLE(VEHICLE_NO) VALUES ( /* 3 */ 'EX0001' )"; SQL statement:
...
```

23505 코드를 설정했을 때는 정상적으로 MyDuplicateKeyException로 처리되는 것을 확인함.



#### MySQL 

* duplicateKeyCodes: `1062`
* https://github.com/spring-projects/spring-framework/blob/8c85c3166c4f9ee6b77862da2229e94162c7cc43/spring-jdbc/src/main/resources/org/springframework/jdbc/support/sql-error-codes.xml#L199

```
01:39:59.522 [main] INFO  org.fp024.study.spring5recipes.vehicle.Main - ### Error code: 1062
01:39:59.522 [main] INFO  org.fp024.study.spring5recipes.vehicle.Main - ### SQL state: 23000
Exception in thread "main" org.springframework.dao.DataIntegrityViolationException: PreparedStatementCallback; SQL [INSERT INTO vehicle (color, wheel, seat, vehicle_no)
VALUES (?, ?, ?, ?)
...
```

😂: 1062로 설정해도 DataIntegrityViolationException로 처리됨.



### HSQLDB

* duplicateKeyCodes: `-104`
* https://github.com/spring-projects/spring-framework/blob/8c85c3166c4f9ee6b77862da2229e94162c7cc43/spring-jdbc/src/main/resources/org/springframework/jdbc/support/sql-error-codes.xml#L136

```
01:41:36.587 [main] INFO  org.fp024.study.spring5recipes.vehicle.Main - ### Error code: -104
01:41:36.588 [main] INFO  org.fp024.study.spring5recipes.vehicle.Main - ### SQL state: 23505
Exception in thread "main" org.springframework.dao.DataIntegrityViolationException: PreparedStatementCallback; SQL [INSERT INTO vehicle (color, wheel, seat, vehicle_no)
VALUES (?, ?, ?, ?)
...
```

😂: -104로 설정해도 DataIntegrityViolationException로 처리됨.






## 의견

* H2만 의도대로 동작하는데... 이부분은 Spring 6.1 프로젝트로 분기해서 확인해봐도 좋을 것 같다.



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

