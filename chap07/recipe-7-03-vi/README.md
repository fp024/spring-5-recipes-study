## 레시피 7-03-vi 유저 인증하기 - UserDetails 캐시하기

> 유저 인증 정보 저장소의 여려 종류를 알아본다.
>

### 이번 레시피에서 확인해야할  내용

* ✔ 인메모리 방식 

* ✔ DB 조회 방식 

* ✔ 패스워드 암호화 

* ✔ LDAP 저장소 
  * Embedded LDAP 적용하여 예제 작성

* ✅ 유저 세부 캐시하기 (번역체라서 좀 이상한데... UserDetails 캐시하기 같다 😅..)
  * 이 내용은 자세하게 경험 해보질 않았는데.. 확실하게 하자.
    

## 진행

우선 저자님 처럼 ehcache를 사용해보고, 이후 다른 캐시([caffeine](https://github.com/ben-manes/caffeine))를 써봐도 좋을 것 같긴한데...

별도로 작성된 저자님의 예제는 없었지만, DB로 회원조회하는 예제에 적용해보자!

저자님 가이드 기준이 ehcache 2.x 기준이긴해서.. 먼저 2.x 기준으로 먼저 해보고 나중에 3.x로 바꿔보자.!



다 넣어보긴 했는데...

```java
  @Bean
  UserDetailsManager userDetailsManager() {
    JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
    userDetailsManager.setUserCache(userCache());
    return userDetailsManager;
  }
```

이것을 설정하면...  로그인 하면  캐시에 로그인 정보가 저장되고, 로그아웃후 로그인을 바로 했을 때.. 유저조회 쿼리가 실행안돼고 캐시에 있는 것을 읽는 것인가? 했는데.... 그건 아닌가보네

> 네, 맞습니다. `userCache`를 설정하면 로그인 정보가 캐시에 저장되지만, 이는 사용자의 세션 정보를 저장하는 것이지 사용자의 조회 쿼리를 대체하는 것은 아닙니다. 따라서 로그아웃 후 다시 로그인하면 사용자 조회 쿼리가 다시 실행됩니다.
>
> `userCache`는 주로 반복적인 사용자 조회를 최적화하는 데 사용됩니다. 예를 들어, 한 세션 내에서 동일한 사용자 정보가 여러 번 필요한 경우에 `userCache`를 사용하면 데이터베이스에 대한 불필요한 쿼리를 줄일 수 있습니다.
>
> 따라서 `userCache`는 성능 최적화 도구로 사용되지만, 로그인 및 로그아웃 프로세스 자체를 대체하거나 변경하지는 않습니다.




## 의견

* 아직 캐시의 장점을 잘 모르겠음.. 😅
  * 예제 프로그램 자체가 복잡하지 않아서 그런가..ㅠㅠ


그런데 진짜 로그인 할 때 캐시로 부터 UserDetails를 읽어들이는 듯한 부분에는

* **AbstractUserDetailsAuthenticationProvider**
  * https://github.com/spring-projects/spring-security/blob/e4e31f2c900fd85a5517b92e5238cea01ac9535d/core/src/main/java/org/springframework/security/authentication/dao/AbstractUserDetailsAuthenticationProvider.java#L129

```java
UserDetails user = this.userCache.getUserFromCache(username);
if (user == null) {
        cacheWasUsed = false;
        try {
            user = retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
            // 이부분에서 결국 DB조회
        }
```

* userCache 값을 보면 항상 NullUserCache 이다. 
* 내가 설정한 SpringCacheBasedUserCache가 안들어가 있음.
* NullUserCache 이면 getUserFromCache() 의 결과 값이 항상 null 이므로 줄줄이 따라가서 현재 UserDetilasService인 `JdbcUserDetailsManager`의 loadUsersByUsername() 호출로 항상 DB 쿼리 수행됨.

결국 AbstractUserDetailsAuthenticationProvider의 userCache 필드에 SpringCacheBasedUserCache 이걸 넣어야 될 것 같은데...

**TODO:  ✨ 그런데 이걸 넣어주는 경로를 못찾겠음. 🤣**



#### 뭔가 이번 장은 혼란의 도가니 중이다. 아무래도 시간을 두고 천천히 봐야할 듯..😅



어떻게든 하려면... 다음과 같은 설정으로 강제 설정을 해줄 수 있는데..

```java
  @Bean
  JdbcUserDetailsManager jdbcUserDetailsManager() {
    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
    jdbcUserDetailsManager.setUserCache(userCache());
    return jdbcUserDetailsManager;
  }

  @Bean
  AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserCache(userCache()); // ✨ 강제 설정
    provider.setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsService(jdbcUserDetailsManager());
    return provider;
  }
```

문제가 또 있음.

* 정말 한번 로그인 하고나면 UserDetails가 캐시가 됨.

  ```
  03:52:02.561 [http-nio-8080-exec-8] DEBUG org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache - Cache put: admin
  ```

  * admin 계정으로 로그인 했을 때. Cache put이 발생함.

* 그러나 캐시를 사용하기 위해 로그아웃 후 로그인했을 때..

  ```
  03:53:20.181 [http-nio-8080-exec-2] DEBUG org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache - Cache hit: true; username: admin
  ```

  * DB 조회 없이 Cache Hit가 되었지만...

  * 캐시에서 가져온 UserDetails의 Password가 null로 초기화 되어있어 다음과 같은 오류가 발생함.

    ```
    java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
    ```



> 😅 이부분은 두번째 오류가 나는 채로 커밋을 해야겠다.
>
> 증상을 알아야하니...



---

## 기타

### Gradle 디펜던시 트리 확인

* compile 디펜던시

  ```sh
  gradle dependencies --configuration=compile
  ```

* runtime 디펜던시

  ```sh
  gradle dependencies --configuration=runtime
  ```

  

### 🙄 스프링에는  Ehcache가 기본 내장되어있다? 

* p409 쪽 스프링에 EhCache가 가본 내장되어있어서, ehcache.xml 설정파일만 포함시켜도 사용할 수 있다는 내용이 있는데, 설명이 잘못된 것 같다.

  * ehcache 디펜던시는 추가해야함.

    ```groovy
      // implementation "org.ehcache:ehcache:${ehcacheVersion}" // ehcache 3.x
      implementation "net.sf.ehcache:ehcache:${ehcacheVersion2x}" // ehcache 2.x
    ```

  * 연동을 쉽게 해서 쓰려면, 서드파티 라이브러리 들을 공통으로 지원해주는 [`spring-context-support`](https://mvnrepository.com/artifact/org.springframework/spring-context-support) 도 디펜던시해야함. 

    ```groovy
    implementation "org.springframework:spring-context-support"
    ```

    * Spring Framework BOM 설정을 해서 버전은 빼둠.

    

#### EhCacheBasedUserCache 는 5.6 부터 폐기됨.

* 사용하려면 SpringCacheBasedUserCache 이거 쓰라는 것 같다.



### JUnit에서 테스트 수행시 EhCache관련 예외 발생할 때..

클래스 하나를 테스트 할 때는 안나지만 프로젝트 단위로 여러 클래스 실행시킬 때 발생함.

```
Caused by: net.sf.ehcache.CacheException: Another unnamed CacheManager already exists in the same VM. Please provide unique names for each CacheManager in the config or do one of following:
1. Use one of the CacheManager.create() static factory methods to reuse same CacheManager with same name or create one if necessary
2. Shutdown the earlier cacheManager before creating new one with same name.
```

https://stackoverflow.com/questions/10013288/another-unnamed-cachemanager-already-exists-in-the-same-vm-ehcache-2-5

> 버전 2.5 이전의 Ehcache 버전에서는 동일한 이름(동일한 구성 리소스)을 가진 CacheManager가 JVM에 존재할 수 있도록 허용했습니다.
>
> Ehcache 2.5 이상에서는 동일한 이름을 가진 여러 CacheManager가 동일한 JVM에 존재하는 것을 허용하지 않습니다. 싱글톤이 아닌 CacheManager를 생성하는 CacheManager() 생성자는 이 규칙을 위반할 수 있습니다.

* 가장 좋은 해결 방법은  테스트 코드에 `@DirtiesContext` 를 붙이는 것이 가장 좋은 해결방법 같다.
* ehcache의 모드를 `shared`로 바꾸라는 내용도 있었는데..
  * 이 경우 이전 테스트 클래스의 캐시 내용이 남아있을 것 같음..😅





## 정오표

* ...
  


---

## JavaDocs

* ...
