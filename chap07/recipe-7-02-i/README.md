## 레시피 7-02-i 웹 애플리케이션 로그인 하기

> 레시피 7-2의 내용을 먼저 읽어보긴 했는데...
>
> 이부분은 예제를 분리하지 말고 하나로 가도 될 것 같긴하다... 미리 진행 부분도 있기도하고..😅



### 이번 레시피에서 확인해야할  내용

* HTTP Basic 인증

* 폼 기반 로그인

  * 커스텀 로그인 페이지

* 로그아웃 서비스

* 익명 로그인 구현

* Remember Me 기능

  

## 진행

### 기본 설정해제 (할필요 없음.😅 개별적으로 비활성화하거나 바꿔서 적용하는것이 추천됨.)

WebSecurityConfigurerAdapter를 사용한다면, 이 클래스의 생성자 옵션으로 끌 수 있긴한데...

```java
	protected WebSecurityConfigurerAdapter(boolean disableDefaults) {
		this.disableDefaults = disableDefaults;
	}
// ...
    // true경우 아래 if 블록내용이 실행 되지 않음.
    if (!this.disableDefaults) {
        applyDefaultConfiguration(this.http);
        ClassLoader classLoader = this.context.getClassLoader();
        List<AbstractHttpConfigurer> defaultHttpConfigurers = SpringFactoriesLoader
            .loadFactories(AbstractHttpConfigurer.class, classLoader);
        for (AbstractHttpConfigurer configurer : defaultHttpConfigurers) {
            this.http.apply(configurer);
        }
    }
// ... 기본 설정들...
	private void applyDefaultConfiguration(HttpSecurity http) throws Exception {
		http.csrf();
		http.addFilter(new WebAsyncManagerIntegrationFilter());
		http.exceptionHandling();
		http.headers();
		http.sessionManagement();
		http.securityContext();
		http.requestCache();
		http.anonymous();
		http.servletApi();
		http.apply(new DefaultLoginPageConfigurer<>());
		http.logout();
	}
```

지금 내 환경이 SecurityFilterChain을 Bean으로 만들어서 사용하는 환경이라 기본 설정을 명시적으로 끄는 생성자가 없다.

뭔가하지 않으려면 기본 설정에서 명시적으로 꺼줘야한다.

```java
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	// ...
    http.formLogin(withDefaults()); // 기본 설정에 포함되지 않아서 제거하면 폼 로그인 기능이 빠짐.
    http.csrf(withDefaults()); // 기본 설정에 포함되기 때문에, 제거하더라도 CSRF 기능은 동작함.
    return http.build();
  }
```

그런데 저자님도.. Note에..

스프링 시큐리티에 기본 구성은 유지화되 전부 끄지 말고 원하지 않는 기능만 해제하는게 좋다고 하셨음..😅

예외 처리나 보안 컨텍스트 연계같은 경우.
`@EnableWebSecurity` 어노테이션이 설정 클래스에 붙어 있을 경우...

* https://github.com/spring-projects/spring-security/blob/5161712c352b99d00c6b59a38987016484fa85c0/config/src/main/java/org/springframework/security/config/annotation/web/configuration/HttpSecurityConfiguration.java#L101

```java
		http
			.csrf(withDefaults())
			.addFilter(webAsyncManagerIntegrationFilter)
			.exceptionHandling(withDefaults())
			.headers(withDefaults())
			.sessionManagement(withDefaults())
			.securityContext(withDefaults())
			.requestCache(withDefaults())
			.anonymous(withDefaults())
			.servletApi(withDefaults())
			.apply(new DefaultLoginPageConfigurer<>());
		http.logout(withDefaults());
```

위의 내용들이 기본으로 설정된다.

391~392에 있는 내용들은 다 기본으로 설정되는 내용임.





### HTTP 기본 인증

CURL을 사용해서 Http Basic 인증 헤더를 넣어서 /todos 로그인

```java
   http.httpBasic(withDefaults());
```

```sh
curl -v -u admin:admin http://localhost:8080/todos
```

명령을 실행하면 todos의 HTML 페이지 코드를 받는 것을 볼 수 있음.



### 폼 기반 로그인

##### 타임리프에서 #session 표현식을 쓸 수 없다.

```
[[${session.getAttribute('SPRING_SECURITY_LAST_EXCEPTION').message]]
```

```
Caused by: java.lang.IllegalArgumentException: The 'request','session','servletContext' and 'response' expression utility objects are no longer available by default for template expressions and their use is not recommended. In cases where they are really needed, they should be manually added as context variables.
```

* 필요한 경우 컨텍스트 변수로 수동 추가하라고 함.

그런데... 다음과 같이 가능했다.

```html
        <th:block th:if="${session.SPRING_SECURITY_LAST_EXCEPTION != null}">
        Reason: [[${session.SPRING_SECURITY_LAST_EXCEPTION.message}]]
        </th:block>
```

실전에서는 메시지 프로퍼티 연결해서 로그인 실패했다고만 출력해주는 것이 나을 것 같다.

저것을 그대로두면 다른 상황에서 노출시키고 싶지 않은 예외 메시지가 나올 것 같아서...










## 의견

* ...



---

## 기타

* ...

  

  

## 정오표

* p396 마지막 코드 블럭에 `</font>` 태그 삭제 필요.
  


---

## JavaDocs

* ...
