## 레시피 9-06-i  ORM 프레임워크 활용하기 - 1

>  ORM 프레임워크 활용하기 - 1 - XML로 객체 매핑
>

### 이번 레시피에서 확인해야할  내용

* ✅ ORM 프레임워크 활용하기 - Hibernate 사용 - XML로 객체 매핑




## 진행

##### 레시피 9-06-i

log4jdbc가 설정되어있어서 그런지 로그가 정말 자세하다.

* ✨ hibernate의 show_sql을 켜면 중복이 되므로 그 옵션은 껏다.

```
$ gradle clean run -Dspring.profiles.active=mysql

> Configure project :
activeProfiles: mysql

> Task :run
11:00:35.238 [main] INFO  org.hibernate.Version - HHH000412: Hibernate ORM core version 5.6.15.Final
11:00:35.431 [main] WARN  org.hibernate.orm.deprecation - HHH90000012: Recognized obsolete hibernate namespace http://hibernate.sourceforge.net/hibernate-mapping. Use namespace http://www.hibernate.org/dtd/hibernate-mapping instead.  Support for obsolete DTD/XSD namespaces may be removed at any time.
11:00:36.211 [main] INFO  org.hibernate.annotations.common.Version - HCANN000001: Hibernate Commons Annotations {5.1.2.Final}
11:00:36.378 [main] WARN  org.hibernate.orm.connections.pooling - HHH10001002: Using Hibernate built-in connection pool (not for production use!)
11:00:36.378 [main] INFO  org.hibernate.orm.connections.pooling - HHH10001005: using driver [null] at URL [jdbc:log4jdbc:mysql://localvmdb.mysql_8:3306/spring_5_recipes_study_chap09]
11:00:36.378 [main] INFO  org.hibernate.orm.connections.pooling - HHH10001001: Connection properties: {password=****, user=springuser}
11:00:36.378 [main] INFO  org.hibernate.orm.connections.pooling - HHH10001003: Autocommit mode: false
11:00:36.382 [main] INFO  org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl - HHH000115: Hibernate connection pool size: 20 (min=1)      
11:00:36.845 [main] INFO  org.hibernate.dialect.Dialect - HHH000400: Using dialect: org.hibernate.dialect.MySQL8Dialect
11:00:37.359 [main] INFO  org.hibernate.orm.connections.access - HHH10001501: Connection obtained from JdbcConnectionAccess [org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator$ConnectionProviderJdbcConnectionAccess@aca3c85] for (non-JTA) DDL execution was not in auto-commit mode; the Connection 'local transaction' will be committed and the Connection will be set into auto-commit mode.
11:00:37.379 [main] DEBUG jdbc.sqltiming -  org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.accept(GenerationTargetToDatabase.java:54)
1. drop table if exists course
 {executed in 13 msec}
11:00:37.383 [main] INFO  org.hibernate.orm.connections.access - HHH10001501: Connection obtained from JdbcConnectionAccess [org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator$ConnectionProviderJdbcConnectionAccess@63dda940] for (non-JTA) DDL execution was not in auto-commit mode; the Connection 'local transaction' will be committed and the Connection will be set into auto-commit mode.
11:00:37.401 [main] DEBUG jdbc.sqltiming -  org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.accept(GenerationTargetToDatabase.java:54)
1. create table course (
       id bigint not null auto_increment,
        title varchar(100) not null,
        begin_date date,
        end_date date,
        fee integer,
        primary key (id)
    ) engine=InnoDB
 {executed in 17 msec}
11:00:37.409 [main] INFO  org.hibernate.engine.transaction.jta.platform.internal.JtaPlatformInitiator - HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
11:00:37.459 [main] INFO  org.fp024.study.spring5recipes.course.Main - commonad args: []

### Course before persisting
Course [id=null, title='Core Spring', beginDate=2007-07-01, endDate=2007-08-01, fee=1000]
11:00:37.561 [main] DEBUG jdbc.sqltiming -  org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.executeUpdate(ResultSetReturnImpl.java:197)
1. insert into course (title, begin_date, end_date, fee) values ('Core Spring', '07/01/2007 00:00:00.000', '08/01/2007 00:00:00.000', 1000)
 {executed in 6 msec}
11:00:37.569 [main] DEBUG jdbc.sqltiming -  org.hibernate.dialect.identity.GetGeneratedKeysDelegate.executeAndExtract(GetGeneratedKeysDelegate.java:61)
1. getGeneratedKeys on query: insert into course (title, begin_date, end_date, fee) values ('Core Spring', '07/01/2007 00:00:00.000', '08/01/2007 00:00:00.000', 1000)    
 {executed in 8 msec}
11:00:37.575 [main] INFO  jdbc.resultsettable -
|--------------|
|generated_key |
|--------------|
|1             |
|--------------|


### Course after persisting
Course [id=1, title='Core Spring', beginDate=2007-07-01, endDate=2007-08-01, fee=1000]
11:00:37.600 [main] DEBUG jdbc.sqltiming -  org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.extract(ResultSetReturnImpl.java:57)
1. select course0_.id as id1_0_0_, course0_.title as title2_0_0_, course0_.begin_date as begin_da3_0_0_, course0_.end_date as end_date4_0_0_, course0_.fee as fee5_0_0_ from course course0_ where course0_.id=1
 {executed in 2 msec}
11:00:37.609 [main] INFO  jdbc.resultsettable -
|---|------------|-----------|-----------|-----|
|id |title       |begin_date |end_date   |fee  |
|---|------------|-----------|-----------|-----|
|1  |Core Spring |2007-07-01 |2007-08-01 |1000 |
|---|------------|-----------|-----------|-----|


### Course fresh from database
Course [id=1, title='Core Spring', beginDate=2007-07-01, endDate=2007-08-01, fee=1000]
11:00:37.616 [main] DEBUG jdbc.sqltiming -  org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.extract(ResultSetReturnImpl.java:57)
1. select course0_.id as id1_0_0_, course0_.title as title2_0_0_, course0_.begin_date as begin_da3_0_0_, course0_.end_date as end_date4_0_0_, course0_.fee as fee5_0_0_ from course course0_ where course0_.id=1
 {executed in 2 msec}
11:00:37.618 [main] INFO  jdbc.resultsettable -
|---|------------|-----------|-----------|-----|
|id |title       |begin_date |end_date   |fee  |
|---|------------|-----------|-----------|-----|
|1  |Core Spring |2007-07-01 |2007-08-01 |1000 |
|---|------------|-----------|-----------|-----|

11:00:37.633 [main] DEBUG jdbc.sqltiming -  org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.executeUpdate(ResultSetReturnImpl.java:197)
1. delete from course where id=1
 {executed in 2 msec}
11:00:37.639 [main] INFO  org.hibernate.orm.connections.pooling - HHH10001008: Cleaning up connection pool [jdbc:log4jdbc:mysql://localvmdb.mysql_8:3306/spring_5_recipes_study_chap09]

BUILD SUCCESSFUL in 5s
5 actionable tasks: 5 executed
...
```

1. AUTO_INCREMENT 가 포함된 INSERT 의 경우는 INSERT 문 실행 후 반환된 키값 까지 보여줌
2. 저장이나 삭제시 그전에 그 엔티티에 대해 조회를 하는 동작도 볼 수 있었음.



이 예제도 mysql 및  hsqldb, h2를 선택해서 사용할 수 있도록 프로필 설정을 유지 했다.

```
$ gradle clean run -Dspring.profiles.active=mysql
$ gradle clean run -Dspring.profiles.active=hsqldb
$ gradle clean run -Dspring.profiles.active=h2
```

* 설정을 전달하지 않을 경우 h2가 기본값.




## 의견

* ...



---

## 기타

* 이 예제에서는 Hibernate가 자동으로 테이블 만들어주는 모습을 보기 위해 지금까지 사용했던 DB 초기화 코드를 제외 했는데, 이후 예제에서는 Hibernate 동작을 NONE 으로 바꾸고 수동 처리해보자!
  
  


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
* 💡
* ...

