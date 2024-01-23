## 레시피 9-06-iii ORM 프레임워크 활용하기 - 3

>  ORM 프레임워크 활용하기 - 2 - JPA 어노테이션 사용
>

### 이번 레시피에서 확인해야할  내용

* ✔ ORM 프레임워크 활용하기 - Hibernate 사용 - XML로 객체 매핑
* ✔ ORM 프레임워크 활용하기 - Hibernate 사용 - JPA  어노테이션 사용
* ✅ ORM 프레임워크 활용하기 - Hibernate 사용 - 하이버네이트를  JPA  엔진으로 사용




## 진행

##### 레시피 9-06-iii

잘변경하였고, `META-INF/persistence.xml`의  JPA  프로퍼티 부분은 DB 선택 사용을 위해 Java 설정에서 처리했다.

```java
  @Bean
  EntityManagerFactory entityManagerFactory() {
    return Persistence.createEntityManagerFactory("course", getJpaProperties());
  }

  private Properties getJpaProperties() {
    Properties jpaProperties = new Properties();

    jpaProperties.setProperty("javax.persistence.jdbc.url", getEnv().getProperty("jdbc.url"));
    jpaProperties.setProperty("javax.persistence.jdbc.user", getEnv().getProperty("jdbc.username"));
    jpaProperties.setProperty(
        "javax.persistence.jdbc.password", getEnv().getProperty("jdbc.password"));
    jpaProperties.setProperty(
        AvailableSettings.DIALECT, getEnv().getProperty("orm.hibernate.dialect"));

    jpaProperties.setProperty(AvailableSettings.SHOW_SQL, String.valueOf(false));
    jpaProperties.setProperty(
        AvailableSettings.HBM2DDL_AUTO, Action.CREATE.getExternalHbm2ddlName());

    return jpaProperties;
  }
```






## 의견

* ...



---

## 기타

#### 커넥션 풀을 별도 설정하지 않으면, 다음 경고가 노출된다.

```
 20:59:22.264 [Test worker] WARN  org.hibernate.orm.connections.pooling - HHH10001002: Using Hibernate built-in connection pool (not for production use!)
```

HikariCP를 붙여보자!

* https://github.com/brettwooldridge/HikariCP/wiki/Hibernate4

* hibernate-hikaricp 라이브러리 추가

  ```groovy
  implementation "org.hibernate:hibernate-hikaricp:${hibernateVersion}"
  ```

* 프로퍼티에 설정 추가

  ```java
      // ✨ HikariCP 설정
      jpaProperties.setProperty(
          "hibernate.connection.provider_class",
          "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");
      jpaProperties.setProperty("hibernate.hikari.minimumIdle", String.valueOf(5));
      jpaProperties.setProperty("hibernate.hikari.maximumPoolSize", String.valueOf(10));
      jpaProperties.setProperty("hibernate.hikari.idleTimeout", String.valueOf(30000));
  ```

이후 로그를 확인해보면...

```
    21:27:34.815 [Test worker] INFO  org.hibernate.engine.jdbc.connections.internal.ConnectionProviderInitiator - HHH000130: Instantiating explicit connection provider: org.hibernate.hikaricp.internal.HikariCPConnectionProvider
    21:27:34.829 [Test worker] INFO  com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Starting...
    21:27:35.171 [Test worker] INFO  com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Start completed.
    ...
```

경고가 없어짐을 확인했다. 👍

`hibernate-hikaricp`가 HikariCP를 낮은 버전(`3.2.0`)을 사용하는데, 굳이 최신 버전을 쓸 필요는 없을 것 같긴한데, 버전을 올려봤을 때 (`5.1.0`).. 딱히 문제는 없었다.

```java
implementation "com.zaxxer:HikariCP:${hikariCpVersion}"
```






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

