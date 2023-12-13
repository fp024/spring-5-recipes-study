## 레시피 7-08 웹플럭스 애플리케이션 보안 기능 추가하기

> 5-06 레시피에 Spring Security를 적용한다.

### 이번 레시피에서 확인해야할  내용

* 

  

## 진행

* 이번도 마찬가지로 main() 실행은

  ```sh
  # main() 실행
  gradle clean run
  ```

* Tomcat에 올려서 실행

  ```sh
  # 소스 경로로 실행
  gradle appRun
  # War로 패키징 하여 실행
  gradle appRunWar
  ```




### 필요없는 라이브러리

저자님 build.gradle 을 보면 아래 라이브러리가 있는데, 현시점에서는 필요없다.

```groovy
    compile "org.springframework.security:spring-security-webflux:5.0.0.M5"
```

그냥 Spring Security에 그냥 통합 되었나봄.



### formLogin 지원

 Spring Security 5.8.x에서는 Webflux의 formLogin 지원이된다. 그 이전도 되었을 것 같긴하지만...

```java
  @Bean
  SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
    return http.authorizeExchange(
            conf ->
                conf.pathMatchers(
                        "/webjars_locator/**", //
                        "/resources/**",
                        "/",
                        "/index",
                        "/welcome",
                        "/welcome/**")
                    .permitAll()
                    .pathMatchers("/reservation*")
                    .hasRole("USER")
                    .pathMatchers("/users/{user}/**")
                    .access(this::currentUserMatchesPath)
                    .anyExchange()
                    .authenticated())
        .formLogin(withDefaults()) // ✨
        .csrf(withDefaults())
        .build();
  }
```

* 요즘 권장하는 lambda 식 방식으로 설정 구성해서 formLogin도 추가했음.





### 5장에서 겪었던 비슷한 문제..

```
...
11:31:17.831 [reactor-http-nio-2] INFO  org.fp024.study.spring5recipes.reactive.court.domain.ReservationValidator - ### clazz: class org.fp024.study.spring5recipes.reactive.court.domain.Reservation
11:31:17.831 [reactor-http-nio-2] INFO  org.fp024.study.spring5recipes.reactive.court.domain.ReservationValidator - ### supports: true
11:31:17.832 [reactor-http-nio-2] DEBUG org.springframework.web.reactive.result.view.ViewResolutionResultHandler - [303edaa7-9] Using 'text/html' given [text/html, application/xhtml+xml, image/avif, image/webp, image/a
png, application/xml;q=0.9, application/signed-exchange;v=b3;q=0.7, */*;q=0.8] and supported [text/html, application/xhtml+xml, application/xml, text/xml, application/rss+xml, application/atom+xml, application/javascri
pt, application/ecmascript, text/javascript, text/ecmascript, application/json, text/css, text/plain, text/event-stream]
11:31:17.833 [reactor-http-nio-2] DEBUG org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository - Found SecurityContext 'SecurityContextImpl [Authentication=UsernamePasswordAuthenticat
ionToken [Principal=org.springframework.security.core.userdetails.User [Username=admin, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, credentialsNonExpired=true, AccountNonLocked=true, Granted Authorities
=[ROLE_ADMIN, ROLE_USER]], Credentials=[PROTECTED], Authenticated=true, Details=null, Granted Authorities=[ROLE_ADMIN, ROLE_USER]]]' in WebSession: 'org.springframework.web.server.session.InMemoryWebSessionStore$InMemo
ryWebSession@fedea75'
11:31:17.833 [reactor-http-nio-2] INFO  org.fp024.study.spring5recipes.reactive.court.domain.ReservationValidator - ### clazz: class org.springframework.security.authentication.UsernamePasswordAuthenticationToken
11:31:17.833 [reactor-http-nio-2] INFO  org.fp024.study.spring5recipes.reactive.court.domain.ReservationValidator - ### supports: false
11:31:17.833 [reactor-http-nio-2] DEBUG org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository - Found SecurityContext 'SecurityContextImpl [Authentication=UsernamePasswordAuthenticat
ionToken [Principal=org.springframework.security.core.userdetails.User [Username=admin, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, credentialsNonExpired=true, AccountNonLocked=true, Granted Authorities
=[ROLE_ADMIN, ROLE_USER]], Credentials=[PROTECTED], Authenticated=true, Details=null, Granted Authorities=[ROLE_ADMIN, ROLE_USER]]]' in WebSession: 'org.springframework.web.server.session.InMemoryWebSessionStore$InMemo
ryWebSession@fedea75'
11:31:17.833 [reactor-http-nio-2] INFO  org.fp024.study.spring5recipes.reactive.court.domain.ReservationValidator - ### clazz: class org.springframework.security.core.context.SecurityContextImpl
11:31:17.833 [reactor-http-nio-2] INFO  org.fp024.study.spring5recipes.reactive.court.domain.ReservationValidator - ### supports: false
11:31:17.833 [reactor-http-nio-2] INFO  org.fp024.study.spring5recipes.reactive.court.domain.ReservationValidator - ### clazz: class org.springframework.security.web.server.csrf.DefaultCsrfToken
11:31:17.833 [reactor-http-nio-2] INFO  org.fp024.study.spring5recipes.reactive.court.domain.ReservationValidator - ### supports: false
11:31:17.834 [reactor-http-nio-2] INFO  org.fp024.study.spring5recipes.reactive.court.domain.ReservationValidator - ### clazz: class org.springframework.web.server.session.InMemoryWebSessionStore$InMemoryWebSession        
11:31:17.834 [reactor-http-nio-2] INFO  org.fp024.study.spring5recipes.reactive.court.domain.ReservationValidator - ### supports: false
11:31:17.896 [reactor-http-nio-2] DEBUG org.springframework.web.server.adapter.HttpWebHandlerAdapter - [303edaa7-9] Completed 200 OK
...
```

ReservationValidator의 supports()가 

Reservation를 검사하고.. 그 이후에... 다음을 줄줄이 지원 여부를 검증하는데..

* UsernamePasswordAuthenticationToken
* SecurityContextImpl
* InMemoryWebSessionStore$InMemoryWebSession
* HttpWebHandlerAdapter

이해가 되지 않는 부분이 지원을 안하면 안하는대로 끝나면 될 것 같은데.. support() 메서드가 실패하면 서버 오류로 끝나버린다.

```
...
11:35:58.635 [reactor-http-nio-2] INFO  org.fp024.study.spring5recipes.reactive.court.domain.ReservationValidator - ### clazz: class org.springframework.security.authentication.UsernamePasswordAuthenticationToken
11:35:58.635 [reactor-http-nio-2] INFO  org.fp024.study.spring5recipes.reactive.court.domain.ReservationValidator - ### supports: false
11:35:58.640 [reactor-http-nio-2] DEBUG org.springframework.web.server.handler.ResponseStatusExceptionHandler - [9a5615a9-9] Resolved [ServerErrorException: "500 INTERNAL_SERVER_ERROR "Failed to invoke: org.fp024.study
.spring5recipes.reactive.court.web.ReservationFormController#initBinder[1 args]"; nested exception is java.lang.IllegalStateException: Invalid target for Validator [org.fp024.study.spring5recipes.reactive.court.domain.
ReservationValidator@189fe930]: UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=admin, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, credentials
NonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_ADMIN, ROLE_USER]], Credentials=[PROTECTED], Authenticated=true, Details=null, Granted Authorities=[ROLE_ADMIN, ROLE_USER]]"] for HTTP GET /reservationF
orm
11:35:58.641 [reactor-http-nio-2] DEBUG org.springframework.web.server.adapter.HttpWebHandlerAdapter - [9a5615a9-9] Completed 500 INTERNAL_SERVER_ERROR
...
```

원인을 알게 되었다.

확인을 더 해보니 컨트롤러의 initBinder 설정에 문제가 있었다.

```java
  @InitBinder("reservation")  // ✨ 검증할 커맨드/폼 속성명을 적어줘야함. 
  public void initBinder(WebDataBinder binder) {
    binder.setValidator(reservationValidator);
  }
```

- https://stackoverflow.com/questions/14533488/adding-multiple-validators-using-initbinder

저걸 써주지 않아서 들어오는것 다검증하려하고 있었음 😅
먼저한 5장도 바꿔두자.. 😅






## 의견

* 이번 예제를 끝으로 7장을 한번 다 보았다. 🎉🎉🎉
* 몇몇 예제는 Deprecated 된 메서드들이 있어서 마이그레이션이 필요한데.. 그 부분은 천천히 해보자.😜




---

## 기타

* ...



## 정오표

* ...

  


---

## JavaDocs

* ...
