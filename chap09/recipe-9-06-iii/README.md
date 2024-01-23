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
* 💡
* ...

