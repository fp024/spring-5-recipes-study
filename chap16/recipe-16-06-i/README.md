## 레시피 16-06-i 통합 테스트에서 트랜잭션 관리하기

> 이제 예제에 DB가 들어가는데, 그냥 하면 재미없으니? `mybatis-dynamic-sql`를 해볼까 했는데, 힘들다..  😅 오랜만에 Quick Start를 보았는데... 역시 복잡하다..

### 이번 레시피에서 확인해야할  내용

* ✅ **16-06-i**: JUnit에서 테스트 컨텍스트 프레임워크의 트랜젝션 관리하기

* ⬜ ...

  




## 진행

### build.gradle에서 프로필을 전달받아 처리할 수 있도록 했다.

```groovy
String getActiveProfiles() {
  final defaultProfile = 'default'
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

final ACTIVE_PROFILES = getActiveProfiles()

tasks.named('test') {
  useJUnitPlatform()
  systemProperty "spring.profiles.active", "${ACTIVE_PROFILES}"
}
```

기본은 default이고, 만약 프로필을 in-mem으로 했을 경우 JDBC DAO를 실행하는 테스트 클래스는 실행되지 않도록 했다.

명령 프롬프트에서 gradle 실행은 다음과 같이한다. (프로필을 지정하지 않으면 `default`로 실행된다.)

```bash
gradle clean test -Dspring.profiles.active=in-mem
```

JDBC DAO를 사용하는 테스트 클래스에는 `@ActiveProfiles`를 `default`로 지정

```java
@SpringJUnitConfig(classes = BankConfiguration.class)
@Transactional
@Sql(scripts = {"classpath:/bank.sql"})
@ActiveProfiles("default")  // 💡 im-mem 프로필일 경우 아래 테스트는 실행되지 않음.
class AccountServiceJUnitContextTests {
  ...
```





## 의견

* ...




---

## 기타

### SonarLint가 어떤 클래스 안에 static 요소 (메서드 또는 클래스)만 있다면 private 생성자를 만들라고 경고를 나타낸다.

이번 예제가 설정 클래스를 하나의 클래스에 모아두기 위해 상위 설정 클래스 하나하고 내부에 프로필 별로 나눈 static 설정 클래스가 2개 있는 구조인데.. 상위 클래스에다가 private 생성자를 넣으라고 sonarlint가 경고를 나타냄. 😓

* **Utility classes should not have public constructors (java:S1118)**
  * 이 경고는 비활성화 해두자..!



### JdbcDaoSupport  를 상속해서 DAO를 사용하면 getJdbcTemplate() 부분에서 NULL이 가능하다고 경고가 뜸.

* 이부분은 DAO가 직접 JdbcTemplate를 주입 받도록 바꾸자!



## 정오표

* ...
  


---

## JavaDocs

* ...



---

### 문서 사용 아이콘

* ✅: Current Done
* ✔: Done
* ⬜: TODO
* ✖️: Skip
* ...

