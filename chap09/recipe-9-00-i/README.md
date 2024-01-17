## 레시피 9-00-i 데이터 액세스

> 9장 데이터 엑세스 시작

### 이번 레시피에서 확인해야할  내용

* ✅ SimpleDataSource + Log4jdbc  + MySQL 8 환경에서 JDBC 예제 작성

* ⬜ HikariCP + Log4jdbc  + MySQL 8 환경에서 예제 작성



## 진행

원초적인 JDBC로 DAO를 만들어서 CRUD 테스트 해보는 예제인데.. 

테이블 생성문을 포함해서 어플리케이션 컨텍스트 로드될 때, 같이 테이블이 생성되도록 하였다.



log4jdbc를 처음 써봤는데... 요즘 환경에서 잘 안되는 것인가 했는데, 생각보다 잘된다.

테스트를 돌려봤는데...

```
04:03:20.104 [Test worker] INFO  org.springframework.test.context.support.DefaultTestContextBootstrapper - Loaded default TestExecutionListener class names from location [META-INF/spring.factories]: [org.springframework.test.context.web.ServletTestExecutionListener, org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener, org.springframework.test.context.event.ApplicationEventsTestExecutionListener, org.springframework.test.context.support.DependencyInjectionTestExecutionListener, org.springframework.test.context.support.DirtiesContextTestExecutionListener, org.springframework.test.context.transaction.TransactionalTestExecutionListener, org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener, org.springframework.test.context.event.EventPublishingTestExecutionListener]
04:03:20.124 [Test worker] INFO  org.springframework.test.context.support.DefaultTestContextBootstrapper - Using TestExecutionListeners: [org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener@3b65e559, org.springframework.test.context.event.ApplicationEventsTestExecutionListener@bae47a0, org.springframework.test.context.support.DependencyInjectionTestExecutionListener@74a9c4b0, org.springframework.test.context.support.DirtiesContextTestExecutionListener@85ec632, org.springframework.test.context.transaction.TransactionalTestExecutionListener@1c05a54d, org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener@65ef722a, org.springframework.test.context.event.EventPublishingTestExecutionListener@5fd9b663]
04:03:20.600 [Test worker] DEBUG jdbc.sqltiming -  org.springframework.jdbc.datasource.init.ScriptUtils.executeSqlScript(ScriptUtils.java:261)
1. DROP TABLE vehicle IF EXISTS
 {executed in 1 msec}
04:03:20.601 [Test worker] DEBUG jdbc.sqltiming -  org.springframework.jdbc.datasource.init.ScriptUtils.executeSqlScript(ScriptUtils.java:261)
1. CREATE TABLE vehicle ( vehicle_no VARCHAR(10) NOT NULL, color VARCHAR(10), wheel INT, seat INT, PRIMARY KEY (vehicle_no) )
 {executed in 1 msec}
04:03:20.631 [Test worker] INFO  org.fp024.study.spring5recipes.vehicle.dao.PlainJdbcVehicleDaoTests - ### delete
04:03:20.743 [Test worker] DEBUG jdbc.sqltiming -  org.fp024.study.spring5recipes.vehicle.dao.PlainJdbcVehicleDao.delete(PlainJdbcVehicleDao.java:125)
2. DELETE
  FROM vehicle
 WHERE vehicle_no = 'TEM0001'
 {executed in 5 msec}
04:03:20.851 [Test worker] DEBUG jdbc.sqltiming -  org.springframework.jdbc.datasource.init.ScriptUtils.executeSqlScript(ScriptUtils.java:261)
3. DROP TABLE vehicle IF EXISTS
 {executed in 1 msec}
04:03:20.852 [Test worker] DEBUG jdbc.sqltiming -  org.springframework.jdbc.datasource.init.ScriptUtils.executeSqlScript(ScriptUtils.java:261)
3. CREATE TABLE vehicle ( vehicle_no VARCHAR(10) NOT NULL, color VARCHAR(10), wheel INT, seat INT, PRIMARY KEY (vehicle_no) )
 {executed in 1 msec}
> Task :test
BUILD SUCCESSFUL in 2s
...
```

커넥션 단위로 묶어서 쿼리가 잘 나오는 모습을 볼 수 있었다.

1. 어플리케이션 컨텍스트 로드시 테이블 DROP, CREATE 한 내용
2. 테스트 코드에서 DELETE 한 내용
3. AfterEach에서 다시 테이블 DROP, CREATE한 내용

위 내용들을 자연스럽게 확인할 수 있었음.



Select 결과에 대해서는 `jdbc.resultsettable` Logger 설정으로 텍스트 테이블 형식으로 볼 수 도 있음.

```
04:21:13.254 [Test worker] DEBUG jdbc.sqltiming -  org.fp024.study.spring5recipes.vehicle.dao.PlainJdbcVehicleDao.findAll(PlainJdbcVehicleDao.java:82)
2. SELECT *
  FROM vehicle
 {executed in 2 msec}
04:21:13.259 [Test worker] INFO  jdbc.resultsettable - 
|-----------|-------|------|-----|
|vehicle_no |color  |wheel |seat |
|-----------|-------|------|-----|
|TEM1000    |Yellow |6     |6    |
|TEM1001    |Gray   |4     |2    |
|-----------|-------|------|-----|
```



| **logger**          | **설명**                                                     |
| ------------------- | ------------------------------------------------------------ |
| jdbc.sqlonly        | SQL만 로그합니다.  prepared statement에서 실행된 SQL은 해당 위치에서 바인드된 데이터로 대체되어 훨씬 더 가독성이 높아집니다 |
| jdbc.sqltiming      | SQL이 실행된 후에 실행된 SQL과 실행에 걸린 시간에 대한 타이밍 통계를 포함하여 로그합니다. |
| jdbc.audit          | ResultSet를 제외한 모든 JDBC 호출을 기록합니다. 이는 매우 방대한 출력이며 특정 JDBC 문제를 추적하지 않는 한 일반적으로 필요하지 않습니다. |
| jdbc.resultset      | ResultSet 객체에 대한 모든 호출이 기록되므로 훨씬 더 방대합니다. |
| jdbc.resultsettable | jdbc 결과를 테이블로 기록합니다. debug 로깅 레벨 은 result set에서 읽지 않은 값을 채웁니다. |
| jdbc.connection     | connection 열기 및 닫기 이벤트를 기록하고 열려 있는 모든 connection 번호를 덤프합니다. 이는 connection 누출 문제를 찾는 데 매우 유용합니다. |

### Log4jdbc

* [Maven Repository: org.bgee.log4jdbc-log4j2 » log4jdbc-log4j2-jdbc4.1 (mvnrepository.com)](https://mvnrepository.com/artifact/org.bgee.log4jdbc-log4j2/log4jdbc-log4j2-jdbc4.1)
* [Log4jdbc-log4j2 (brunorozendo.com)](https://log4jdbc.brunorozendo.com/)

* 다른 곳.. 이건 다른 것 같은데...
  * https://github.com/arthurblake/log4jdbc
  * https://code.google.com/archive/p/log4jdbc/downloads



### 예제 실행

Main 클래스의 main을 실행하면 되므로 다음과 같이 실행해주면 된다.

```bash
gradle clean run
```








## 의견

* log4jdbc 이걸 사용해본게 제일 큰 수확이였다.. 😊👍
  * Google Code에 있는 소스를 GitHub로 Export 해둔 레파지토리 같음
    * https://github.com/joaoraf/log4jdbc-log4j2




---

## 기타

* ...



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

