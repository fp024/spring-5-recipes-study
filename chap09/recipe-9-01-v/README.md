## 레시피 9-01-iv JDBC 템플릿으로 DB 수정하기 - 5

> 레시피 9-01-v의 JDBC Template insert 반복 코드를  batchUpdate()로 수정
>

### 이번 레시피에서 확인해야할  내용

* ✔ `PreparedStatementCreator` 를 활용해서 insert() 수정

* ✔  JDBC Template 사용처를 `PreparedStatementCreator`를 사용한 람다식으로 변경

* ✔  JDBC Template 사용처를 `PreparedStatementSetter`를 사용한 람다식으로 변경

* ✔  JDBC Template 사용처를 SQL 문과 매개변수 값 전달 방식으로 수정

* ✅  JDBC Template insert 반복 코드를  batchUpdate()로 수정




## 진행

##### 레시피 9-01-v

#### JDBC Template insert 반복 코드를  batchUpdate()로 수정

```java
  // 이전: Collection의 INSERT
  //   VehicleDao 인터페이스에 정의 되어있다.
  default void insert(Collection<Vehicle> vehicles) {
    vehicles.forEach(this::insert);
  }
```

```java
  // 변경: batchUpdate()로 변경
  @Override
  public void insert(Collection<Vehicle> vehicles) {
    jdbcTemplate.batchUpdate(INSERT_SQL, vehicles, vehicles.size(), this::prepareStatement);
  }
```

* `ParameterizedPreparedStatementSetter<T>` 를 사용함
  * https://github.com/spring-projects/spring-framework/blob/v5.3.31/spring-jdbc/src/main/java/org/springframework/jdbc/core/ParameterizedPreparedStatementSetter.java

##### 실행 로그

```
...
02:01:54.191 [main] DEBUG jdbc.sqltiming -  com.zaxxer.hikari.pool.ProxyStatement.executeBatch(ProxyStatement.java:127)
1. batching 3 statements:
1:  INSERT INTO vehicle (color, wheel, seat, vehicle_no)
VALUES ('Yellow', 4, 6, 'TEM2001')

2:  INSERT INTO vehicle (color, wheel, seat, vehicle_no)
VALUES ('Silver', 4, 4, 'TEM2002')

3:  INSERT INTO vehicle (color, wheel, seat, vehicle_no)
VALUES ('White', 4, 2, 'TEM2003')
 {executed in 13 msec}
02:01:54.213 [SpringContextShutdownHook] INFO  com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Shutdown initiated...

```

`log4jdbc`를 통해 로그를 좀 더 자세히 볼 수 있는데 3개의 INSERT 가 배치단위로 한번에 처리된 것을 볼 수 있다.






## 의견

* ...



---

## 기타







## 정오표

* ...
  


---

## JavaDocs

* ...



---

### 문서 사용 아이콘

* ✅
* ✔
* ⬜
* ...

