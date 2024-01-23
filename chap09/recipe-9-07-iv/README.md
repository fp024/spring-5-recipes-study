## 레시피 9-07-ii 스프링에서 ORM 리소스 팩토리 구성하기 - 4 ~ 5

>  스프링에서 ORM 리소스 팩토리 구성하기 - 4 - 

### 이번 레시피에서 확인해야할  내용

* ✔ 스프링에서 ORM 리소스 팩토리 구성하기 - 1 - LocalSessionFactoryBean 사용

* ✔ 스프링에서 ORM 리소스 팩토리 구성하기 - 2 - DataSource를 별도 생성해서 주입

* ✔ 스프링에서 ORM 리소스 팩토리 구성하기 - 3 - LocalEntityManagerFactoryBean 사용

* ✅ 스프링에서 ORM 리소스 팩토리 구성하기 - 4 - LocalContainerEntityManagerFactoryBean사용

  




## 진행

### 레시피 9-07-iv

이번에는 LocalEntityManagerFactoryBean 대신 `LocalContainerEntityManagerFactoryBean`을 사용하도록 코드 변경

* `LocalContainerEntityManagerFactoryBean`에는 바로 DataSource 빈을 지정할 수 있음
* Hibernate Properties에 연결정보와 `hibernate-hikaricp`라이브러리를 제거했다.
* 설정에 모든 정보가 포함되서 `META-INF/persistence.xml` 도 제거함



JpaVendorAdapter에 그대로 존재하는 건 

```java
  private JpaVendorAdapter jpaVendorAdapter() {
    HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
    jpaVendorAdapter.setShowSql(false);
    // jpaVendorAdapter.setGenerateDdl(true); // CREATE는 아니고 UPDATE에 해당
    jpaVendorAdapter.setDatabasePlatform(getEnv().getProperty("orm.hibernate.dialect"));
    return jpaVendorAdapter;
  }

  private Properties hibernateProperties() {
    Properties props = new Properties();
    props.setProperty(AvailableSettings.HBM2DDL_AUTO, Action.CREATE.getExternalHbm2ddlName());
    props.setProperty(AvailableSettings.FORMAT_SQL, String.valueOf(true));
    return props;
  }
```



* jpaVendorAdapter.setGenerateDdl(true)는 Update 는 아니여서 이것을 설정해보면 .. 아래와 같이 테이블 생성전에 DB의 스키마 정보를 조회해 보는 것을 알 수 있다.

* H2 나 HSQLDB는 다음 처럼 티가 나는데..

  ```
  1. select * from INFORMATION_SCHEMA.SEQUENCES
   {executed in 3 msec}
  01:06:14.588 [main] INFO  jdbc.resultsettable -
  |-----------------|----------------|--------------|----------|------------------|------------------------|--------------|------------|--------------|--------------|----------|-------------|-------------------|---------------------------|-----------------------|-----------|------|--------|
  |sequence_catalog |sequence_schema |sequence_name |data_type |numeric_precision |numeric_precision_radix |numeric_scale |start_value |minimum_value |maximum_value |increment |cycle_option |declared_data_type |declared_numeric_precision |declared_numeric_scale |base_value |cache |remarks |
  |-----------------|----------------|--------------|----------|------------------|------------------------|--------------|------------|--------------|--------------|----------|-------------|-------------------|---------------------------|-----------------------|-----------|------|--------|
  |-----------------|----------------|--------------|----------|------------------|------------------------|--------------|------------|--------------|--------------|----------|-------------|-------------------|---------------------------|-----------------------|-----------|------|--------|
  ...
  ```

* MySQL 은 로그상에 특이한 부분이 나타나진 않음. 뭔가 바로 판단할 수 있는 내부구문을 실행하는 것 같음.

  

* `jpaVendorAdapter.setDatabasePlatform(getEnv().getProperty("orm.hibernate.dialect"))` 관련해서는 이 코드는 없어도 잘동작했다. Hibernate 6.4.x 에서는 이 설정이 넣지 않기를 권고하고 넣는 다면 오류난다.



### 레시피 9-07-v

* 이번 예제에 v의 내용을 포함했다. 
  * 엔티티를 패키지 스캔 하는 부분
  * `META-INF/persistence.xml` 속 설정을 모두 Java 설정으로 커버하게 해서, 해당 파일 지움






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

