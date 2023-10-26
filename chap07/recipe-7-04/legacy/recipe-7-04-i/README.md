## 레시피 7-04-i 접근 통제 결정하기  - 커스텀 거수기 작성 - `레거시`

> ...
>

### 이번 레시피에서 확인해야할  내용

* ✅ 커스텀 거수기 작성

* ⬜ 표현식을 이용해 접근 통제 결정하기
  
* ⬜ 스프링 빈을 표현식에 넣어 접근 통제 결정하기
  
  

## 진행

이번 예제는 스프링 시큐리티 5.8에서 수행하기가 애매하다.

폐기되거나 아예 사용할 수 없는 메서드들이 있다. 이 예제를 5.8환경에서 수행하기는 매우 힘들 것 같다.



### 폐기 목록

1. AffirmativeBased 대신 AuthorizationManager 사용
2. AuthenticatedVoter 대신 org.springframework.security.authorization.AuthorityAuthorizationManager  사용

저자님 예제도 코드를 보아도 뭔가 진행이되다 말은 것 같음.😅

아무래도 이전 버전으로 먼저 해보고, 최신 버전에 맞게 변경하는것이 나을 것 같은데...



## 🎃 문제 👿

확실히 문제가 있는데 모르겠음.

Spring Security의 버전은 저자님과 거의 비슷하게 `5.0.19.RELEASE`로 낮추었는데도 의도대로 실행할 수 없음.

```java
  @Bean
  public AffirmativeBased accessDecisionManager() {
    List<AccessDecisionVoter<?>> decisionVoters =
        List.of(
            new AuthenticatedVoter(),
            new IpAddressVoter(),
            new RoleVoter(),
            new WebExpressionVoter());
    AffirmativeBased decisionManager = new AffirmativeBased(decisionVoters);
    return decisionManager;
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .accessDecisionManager(accessDecisionManager())
        .antMatchers(
            "/webjars/**",
            "/resources/**", //
            "/login", //
            "/logout-success", //
            "/",
            "/index",
            "/favicon.ico")
        .permitAll()
        .antMatchers(HttpMethod.DELETE, "/todos/*")
        .access("IP_LOCAL_HOST") //  ✨IP_LOCAL_HOST 규칙 적용
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .loginPage("/login") //
        .loginProcessingUrl("/login")
        .defaultSuccessUrl("/todos")
        .failureUrl("/login?error=true")
        .and()
        .rememberMe()
        .userDetailsService(jdbcUserDetailsManager())
        .and()
        .logout()
        .logoutSuccessUrl("/logout-success");
  }
```





## 해결

일단 다음과 같은 표현식은 쓸 수 없음. 

```java
// 1)
.access("IP_LOCAL_HOST,ADMIN")

//2)
.access("IP_LOCAL_HOST and hasAuthority('ADMIN')")

```



단순화하기 위해서 다른 부분을 전부 허용하고 Todo를 삭제할 때만 조건을 체크하게 설정을 바꿔봤음.

```java
  @Bean
  public AffirmativeBased accessDecisionManager() {
    List<AccessDecisionVoter<?>> decisionVoters =
        List.of(new AuthenticatedVoter(), new IpAddressVoter());
    AffirmativeBased decisionManager = new AffirmativeBased(decisionVoters);
    return decisionManager;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .accessDecisionManager(accessDecisionManager())
        .antMatchers(HttpMethod.DELETE, "/todos/*")
        .access("IP_LOCAL_HOST")
        //
        .and()
        .formLogin()
        .loginPage("/login") //
        .loginProcessingUrl("/login")
        .defaultSuccessUrl("/todos")
        .failureUrl("/login?error=true")
        .and()
        .rememberMe()
        .userDetailsService(jdbcUserDetailsManager())
        .and()
        .logout()
        .logoutSuccessUrl("/logout-success");
  }
```

이렇게 설정한 후, IpAddressVoter 도 수정이 필요했는데..

```java

@Slf4j
public class IpAddressVoter implements AccessDecisionVoter<Object> {

  private static final String IP_PREFIX = "IP_";
  private static final String IP_LOCAL_HOST = "IP_LOCAL_HOST";

  @Override
  public boolean supports(ConfigAttribute attribute) {
    // WebExpressionConfigAttribute 타입으로 들어오는데..
    // attribute.getAttribute()의 반환값이 항상 null이였다.
    return (attribute.toString() != null) && attribute.toString().startsWith(IP_PREFIX); // ✨ 여전히 null
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }

  @Override
  public int vote(
      Authentication authentication, Object object, Collection<ConfigAttribute> configList) {
    if (!(authentication.getDetails() instanceof WebAuthenticationDetails details)) {
      return ACCESS_DENIED;
    }

    String address = details.getRemoteAddress();

    int result = ACCESS_ABSTAIN;

    for (ConfigAttribute config : configList) {
      result = ACCESS_DENIED;
      LOGGER.info("### config: {}", config);
      LOGGER.info("### config.getAttribute(): {}", config.getAttribute()); // ✨ 여전히 null
      if (Objects.equals(IP_LOCAL_HOST, config.toString())) { // toString으로 비교해야 조건이 맞음.
        if (address.equals("127.0.0.1") || address.equals("0:0:0:0:0:0:0:1")) {
          return ACCESS_GRANTED;
        }
      }
    }

    return result;
  }
}

```

🎃 ConfigAttribute의 getAttribute()로 불러오면 항상 null이 되서 허용하지 않는 상태가 되어버림

✨ toString()으로 값을 받아오면 `.access("IP_LOCAL_HOST")`에 넘겨준 IP_LOCAL_HOST를 인식해서 의도대로 동작함.

하고자 하는대로 동작을 맞추긴 했지만... 물론 이렇게 하는것이 올바른지는 모르겠음. 🎃






## 의견

* 레거시 버전에서 의도대로 하게는 했음.
* 이제 요구사항 맞춰서 최신 버전으로 어떻게 바꿀지가 문제같다..



---

## 기타

* ...





## 정오표

* p414 마지막 코드 예제

  ```java
  .accessDecisionManager() 
  // 다음처럼 변경
  .accessDecisionManager(accessDecisionManager())
  ```

* p414 마지막 코드 예제에 access를 쉼표로 구분한 것은 확실히 이상하긴한데...

  ```java
  http.authorizeRequests()
   .accessDecisionManager()
   .antMatchers(HttpMethod.DELETE, "/todos*").access("ADMIN,IP_LOCAL_HOST"); // 이상함..🎃
  ```

  




---

## JavaDocs

* ...


## 문서 아이콘 모음.
* ✔ ...
* ✅ ...
* ⬜ ...