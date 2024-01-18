## 레시피 9-00-ii 데이터 액세스 - HikariCP, MySQL 8 적용

> 레시피 9-00-i에  HikariCP만 포함시킨 내용이라, 거기에 포함시킬려다가...
>
> HikariCP와 Log4jdbc를 같이 사용할 때..Connection.setNetworkTimeout() 관련 에러로그가 남는 현상이 있어서 
>
> 프로젝트를 분리했다.  그래서 이번 레시피에서는 DB를 MySQL 8로 바꿀 것인데... 
>
> MySQL Connector/J 드라이버에 setNetworkTimeout() 메서드가 구현되어있어서 문제는 없을 것으로 보인다. 😅



### 이번 레시피에서 확인해야할  내용

* ✔ SimpleDataSource + Log4jdbc  + MySQL 8 환경에서 JDBC 예제 작성
  
* ✅ HikariCP + Log4jdbc  + MySQL 8 환경에서 예제 작성



## 진행

이 내용을 보았을 때..  다른 부분은 다 동일하고 데이터소스 부분만 hikaricp로 바꾼 내용이라 이 프로젝트를 수정했다.



HikariCP를 붙이고 나서부터 실행할 때 다음 오류가 나는데....

```
05:08:45.567 [main] INFO  com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Starting...
05:08:45.750 [main] ERROR jdbc.sqltiming - 1. Connection.setNetworkTimeout(java.util.concurrent.ThreadPoolExecutor@488eb7f2[Running, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 0], 5000;      
java.sql.SQLFeatureNotSupportedException: feature not supported
        at org.hsqldb.jdbc.JDBCUtil.notSupported(Unknown Source) ~[hsqldb-2.7.2.jar:2.7.2]
        at org.hsqldb.jdbc.JDBCConnection.setNetworkTimeout(Unknown Source) ~[hsqldb-2.7.2.jar:2.7.2]
        at net.sf.log4jdbc.sql.jdbcapi.ConnectionSpy.setNetworkTimeout(ConnectionSpy.java:1120) ~[log4jdbc-log4j2-jdbc4.1-1.16.jar:?]
        at com.zaxxer.hikari.pool.PoolBase.getAndSetNetworkTimeout(PoolBase.java:529) ~[HikariCP-5.1.0.jar:?]
        ....
05:08:45.761 [main] INFO  com.zaxxer.hikari.pool.PoolBase - HikariPool-1 - Driver does not support get/set network timeout for connections. (feature not supported)
05:08:45.771 [main] INFO  com.zaxxer.hikari.pool.HikariPool - HikariPool-1 - Added connection net.sf.log4jdbc.sql.jdbcapi.ConnectionSpy@2bb7bd00
05:08:45.773 [main] INFO  com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Start completed.

```

실행에는 문제가 없었지만...

* https://www.cubrid.com/qna/3841762

* https://github.com/ryenus/hsqldb/blob/b1a6ac86c0956efc47dbc728436e53e95c3f4ed9/src/org/hsqldb/jdbc/JDBCConnection.java#L3073
* https://github.com/ryenus/hsqldb/blob/b1a6ac86c0956efc47dbc728436e53e95c3f4ed9/src/org/hsqldb/jdbc/JDBCUtil.java#L96



원인은 HikariCP를 사용하게 되면서... setNetworkTimeout() 호출이 일어나게 되는데,  HSQLDB의 JDBCConnection.setNetworkTimeout() 을 호출하게 되었을 때 지원하지 않는다고 예외 던져지게 되고, 

이걸 HikariCP는 오류 아니라고 판단해서 INFO로 로깅을 하는데.. log4jdbc는 ERROR 로그 레벨로 노출해서 그런 것 같다.

H2 DB는 어떻게 되어있나보았는데 여기도 지원하지 않는다.

* https://github.com/h2database/h2database/blob/19b770ec010a621989a980bf166a10ac10072a61/h2/src/main/org/h2/jdbc/JdbcConnection.java#L1847

  > ✨ 그런데 여기서는 해당 메서드 구현이 비어있기 때문에 예외가 발생하지 않아, 에러로그가 나타나진 않을 것 같다. 😅

MySQL의 Connection/J에는 구현이 되어있음.

* https://github.com/mysql/mysql-connector-j/blob/release/8.x/src/main/user-impl/java/com/mysql/cj/jdbc/ConnectionImpl.java

일단 커밋을 하고 MySQL로 바꿔서 다시 확인해보자! 😅



### MySQL 8 환경에서 확인을 했다.. - 이제 ERROR 로그가 노출되지 않음. 👍

jdbc.sqltiming 의 ERROR로그 뿐만아니라, HikariCP의 `Driver does not support get/set network timeout for connections. (feature not supported)` 알림도 노출되지 않는다.

```
19:47:32.514 [Test worker] INFO  org.springframework.test.context.support.DefaultTestContextBootstrapper - Loaded default TestExecutionListener class names from location [META-INF/spring.factories]: [org.springframework.test.context.web.ServletTestExecutionListener, org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener, org.springframework.test.context.event.ApplicationEventsTestExecutionListener, org.springframework.test.context.support.DependencyInjectionTestExecutionListener, org.springframework.test.context.support.DirtiesContextTestExecutionListener, org.springframework.test.context.transaction.TransactionalTestExecutionListener, org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener, org.springframework.test.context.event.EventPublishingTestExecutionListener]
19:47:32.537 [Test worker] INFO  org.springframework.test.context.support.DefaultTestContextBootstrapper - Using TestExecutionListeners: [org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener@5fd9b663, org.springframework.test.context.event.ApplicationEventsTestExecutionListener@214894fc, org.springframework.test.context.support.DependencyInjectionTestExecutionListener@10567255, org.springframework.test.context.support.DirtiesContextTestExecutionListener@e362c57, org.springframework.test.context.transaction.TransactionalTestExecutionListener@1c4ee95c, org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener@79c4715d, org.springframework.test.context.event.EventPublishingTestExecutionListener@5aa360ea]
19:47:32.841 [Test worker] INFO  com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Starting...
19:47:33.176 [Test worker] INFO  com.zaxxer.hikari.pool.HikariPool - HikariPool-1 - Added connection net.sf.log4jdbc.sql.jdbcapi.ConnectionSpy@a5272be
19:47:33.178 [Test worker] INFO  com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Start completed.
19:47:33.219 [Test worker] DEBUG jdbc.sqltiming -  com.zaxxer.hikari.pool.ProxyStatement.execute(ProxyStatement.java:94)
1. DROP TABLE IF EXISTS vehicle
 {executed in 19 msec}
19:47:33.238 [Test worker] DEBUG jdbc.sqltiming -  com.zaxxer.hikari.pool.ProxyStatement.execute(ProxyStatement.java:94)
1. CREATE TABLE vehicle ( vehicle_no VARCHAR(10) NOT NULL, color VARCHAR(10), wheel INT, seat INT, PRIMARY KEY (vehicle_no) )
 {executed in 18 msec}
19:47:33.243 [Test worker] DEBUG jdbc.sqltiming -  com.zaxxer.hikari.pool.ProxyStatement.execute(ProxyStatement.java:94)
1. INSERT INTO vehicle (vehicle_no, color, wheel, seat) VALUES ('TEM1000', 'Yellow', 6, 6)
 {executed in 5 msec}
19:47:33.248 [Test worker] DEBUG jdbc.sqltiming -  com.zaxxer.hikari.pool.ProxyStatement.execute(ProxyStatement.java:94)
1. INSERT INTO vehicle (vehicle_no, color, wheel, seat) VALUES ('TEM1001', 'Gray', 4, 2)
 {executed in 3 msec}
19:47:33.306 [Test worker] INFO  org.fp024.study.spring5recipes.vehicle.dao.PlainJdbcVehicleDaoTests - ### findAll
19:47:33.328 [Test worker] DEBUG jdbc.sqltiming -  com.zaxxer.hikari.pool.ProxyPreparedStatement.executeQuery(ProxyPreparedStatement.java:52)
1. SELECT *
  FROM vehicle
 {executed in 10 msec}
19:47:33.338 [Test worker] INFO  jdbc.resultsettable - 
|-----------|-------|------|-----|
|vehicle_no |color  |wheel |seat |
|-----------|-------|------|-----|
|TEM1000    |Yellow |6     |6    |
|TEM1001    |Gray   |4     |2    |
|-----------|-------|------|-----|

19:47:33.421 [Test worker] DEBUG jdbc.sqltiming -  com.zaxxer.hikari.pool.ProxyStatement.execute(ProxyStatement.java:94)
1. DROP TABLE IF EXISTS vehicle
 {executed in 11 msec}
19:47:33.442 [Test worker] DEBUG jdbc.sqltiming -  com.zaxxer.hikari.pool.ProxyStatement.execute(ProxyStatement.java:94)
1. CREATE TABLE vehicle ( vehicle_no VARCHAR(10) NOT NULL, color VARCHAR(10), wheel INT, seat INT, PRIMARY KEY (vehicle_no) )
 {executed in 20 msec}
19:47:33.447 [Test worker] DEBUG jdbc.sqltiming -  com.zaxxer.hikari.pool.ProxyStatement.execute(ProxyStatement.java:94)
1. INSERT INTO vehicle (vehicle_no, color, wheel, seat) VALUES ('TEM1000', 'Yellow', 6, 6)
 {executed in 4 msec}
19:47:33.450 [Test worker] DEBUG jdbc.sqltiming -  com.zaxxer.hikari.pool.ProxyStatement.execute(ProxyStatement.java:94)
1. INSERT INTO vehicle (vehicle_no, color, wheel, seat) VALUES ('TEM1001', 'Gray', 4, 2)
 {executed in 3 msec}
> Task :test
BUILD SUCCESSFUL in 2s
```



### 예제 실행

Main 클래스의 main을 실행하면 되므로 다음과 같이 실행해주면 된다.

```bash
gradle clean run
```






## 의견

* 9장의 나머지 레시피도 그냥 MySQL 8 써야겠다...😅



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

