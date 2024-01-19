## 레시피 9-01-iii JDBC 템플릿으로 DB 수정하기 - 3

> 레시피 9-01-ii의 JDBC Template 사용처를 `PreparedStatementSetter`를 사용한 람다식으로 변경
>

### 이번 레시피에서 확인해야할  내용

* ✔ `PreparedStatementCreator` 를 활용해서 insert() 수정

* ✔  JDBC Template 사용처를 `PreparedStatementCreator`를 사용한 람다식으로 변경

* ✅  JDBC Template 사용처를 `PreparedStatementSetter`를 사용한 람다식으로 변경




## 진행

##### 레시피 9-01-iii

#### JDBC Template 사용처를 `PreparedStatementSetter`를 사용한 람다식으로 변경

```java
  // 이전: PreparedStatementCreator
  @Override
  public void insert(Vehicle vehicle) {
    jdbcTemplate.update(
        conn -> {
          PreparedStatement ps = conn.prepareStatement(INSERT_SQL);
          prepareStatement(ps, vehicle);
          return ps;
        });
  }
```

```java
  // 변경: PreparedStatementSetter
  @Override
  public void insert(Vehicle vehicle) {
    jdbcTemplate.update(INSERT_SQL, ps -> prepareStatement(ps, vehicle));
  }
```



#### 프로젝트 구성 수정

9-01-ii  에서 진행했던 통합 테스트 나누고 했던 내용이 복잡한 것 같아서, 다시 합쳤다..ㅠㅠ

일반 테스트 초기화시 두번 초기화가 발생하더라도 이렇게 두는게 나을 것 같다.

그외에, 

 IntelliJ는 기본 테스트 환경을 Gradle Test로 설정해서 실행시킬 수 있는데,  VSCode에서는 기본 테스트는 무조건 Java JUnit으로 실행시키기 때문에 build.gradle에 설정한 프로필 지정 로직이 적용되지 않아 테스트가 실패했다.

그래서 이 부분을  프로필이 지정되지 않아도 기본으로 수행되도록 바꿨다.

* MySQL:  mysql 프로필 또는 default 프로필에서 구성 클래스 적용

  ```java
  @Profile({"mysql", "default"})
  @Configuration
  @PropertySource("classpath:database-mysql.properties")
  public class MySqlDatabaseConfiguration extends DatabaseConfiguration {
    ...
  ```

* HSQLDB: default 프로필이 설정되지 않은 상태에서 hsqldb 프로필이 설정되었을 때, 구성 적용

  ```java
  @Profile({"hsqldb & !default"})
  @Configuration
  @PropertySource("classpath:database-hsqldb.properties")
  public class HsqldbDatabaseConfiguration extends DatabaseConfiguration {
    ...
  ```

* H2:  default 프로필이 설정되지 않은 상태에서 h2 프로필이 설정되었을 때, 구성 적용

  ```java
  @Profile({"h2 & !default"})
  @Configuration
  @PropertySource("classpath:database-h2.properties")
  public class H2DatabaseConfiguration extends DatabaseConfiguration {
    ...
  ```

* build.gradle

  ```groovy
  String getActiveProfiles() {
    final defaultProfile = 'default'  // ✨ 빈값을 넣어도 되긴 하는데, default로 명시
    def activeProfiles = System.getProperty("spring.profiles.active")
    if (!activeProfiles?.trim()) {
      logger.quiet("activeProfiles is null or empty: ${activeProfiles}")
      logger.quiet("It runs with the default profile: ${defaultProfile}")
      return defaultProfile
    } else {
      logger.quiet("activeProfiles: ${activeProfiles}")
      return "${activeProfiles}"
    }
  }
  ```

위와 같이 설정하고나서 VSCode의 기본 테스트 메뉴에서도 테스트를 정상 수행할 수 있음을 확인했다.






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

