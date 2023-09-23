## 레시피 5-01-i 트롤러에서 TaskExecutor로 요청을 비동기 처리 (ii ~ iv 포함)

> ...
>

### 이번 레시피에서 확인해야할  내용

* 요청을 비동기 처리해서 서블릿 컨테이너의 부하를 줄임.

  
  
  

## 진행

#### 비동기 컨트롤러 메서드

1. 코트 예약 조회
   * http://localhost:8080/reservationQuery
2. 코트 예약 등록
   * http://localhost:8080/reservationForm 



HTML에서 아무처리가 없으면 그냥 화면이 아무동작도 하지 않는 것 처럼 보이게되는데...

1. 요청후 일단 내용이 빈 200응답 받음
   1. 서버측에서는 별도 스레드에서 처리중
2. 서버측 처리 완료되면 응답

그래서 버튼에 회전하는 스패너를 추가했다.

```html
<!-- 페이지를 처음 로드했을 때는 스패너가 비활성화 -->
<div class="input-group">
  <button class="btn btn btn-secondary" type="submit" value="Query">
    <span role="status">Query</span>
    <span class="spinner-border spinner-border-sm d-none" aria-hidden="true"></span>
  </button>
</div>
...

<!-- Sumit 버튼을 눌렀다면 스패너를 보이게하고 버튼 잠굼 --> 
<script>
  document.querySelector('form').addEventListener('submit', function(event) {
    event.preventDefault();
    document.querySelector('.spinner-border').classList.remove('d-none');
    this.querySelector('button[type="submit"]').disabled = true;
    this.submit();
  });
</script>
```

* 서버측에서 포워드를 하면 다시 스패너는 숨겨짐
* 서버측에서 리다이렉트를 할 경우는 다른 페이지로 이동해버리므로 문제없음.



### 테스트 작성

```java
  // 비동기 컨트롤러 메서드 테스트
  @Test
  void testSubmitForm() throws Exception {
    // 200 응답만 확인, 비동기라서 이때는 아직 모델에 값들이 없다.
    MvcResult mvcResult =
        mockMvc
            .perform(
                post("/reservationQuery/") //
                    .param("courtName", "Tennis #1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

    // mvc 결과로 async 처리 확인
    mockMvc
        .perform(asyncDispatch(mvcResult))
        .andDo(print())
        .andExpect(model().attributeExists("reservations"))
        .andExpect(model().attribute("reservations", hasSize(1)))
        .andExpect(view().name("reservationQuery"))
        .andDo(print());
  }
```

* 한번 요청 검증을 하고 asyncDispatch로 추가 검증하는식으로 처리





### 비동기 처리 설정 관련?

`CourtServletContainerInitializer`에 아래 코드를 설정해주라는 언급이 있는데..

```java
  @Override
  public void onStartup(ServletContext ctx) {
    DispatcherServlet servlet = new DispatcherServlet();
    ServletRegistration.Dynamic registration = ctx.addServlet("dispatcher", servlet);
    registration.setAsyncSupported(true);
  }
```

위 코드를 넣으면 시작이 스프링 시작이 안된다. (tomcat 9, jetty10 모두)

* 이 설정의 경우는 설정 클래스 들과 연관이 안되서 그런 것 같다.
* Tomcat으로 실행하면 로그가 스프링 시작로그가 안나왔지만, Jetty의 경우는 dispatch-servlet.xml이 없다는 에러로그가 나왔다.
* 다음 페이지를 읽어보면 이미 켜져있다고 친절하게 나옴 😂



AbstractDispatcherServletInitializer를 보면 이미 기본 값이 true이 것 같은데...

```java
	/**
	 * A single place to control the {@code asyncSupported} flag for the
	 * {@code DispatcherServlet} and all filters added via {@link #getServletFilters()}.
	 * <p>The default value is "true".
	 */
	protected boolean isAsyncSupported() {
		return true;
	}
```

역자님 예제를 보았을 때는 onStartup()에 해당 설정이 없는 상태였다.

저자님 예제는 AbstractDispatcherServletInitializer를 상속한 클래스 자체가 없음 😅

비동기 컨트롤러 메서드를 만들었더라도, HTML에서 일반 폼전송을 해버리면  `브라우저 <--> 서버`  사이는 그냥 동기식 처럼 동작하는 것 같음.

브라우저 네트워크 탭을 보면서 Submit을 해보면 그런 모습이 보임.



```
04:49:43.305 [http-nio-8080-exec-4] DEBUG org.springframework.web.servlet.DispatcherServlet - POST "/reservationQuery", parameters={masked}
04:49:43.305 [http-nio-8080-exec-4] DEBUG org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped to org.fp024.study.spring5recipes.court.web.ReservationQueryController#submitForm(Str
ing, Model)
04:49:43.306 [http-nio-8080-exec-4] DEBUG org.springframework.web.context.request.async.WebAsyncManager - Started async request
04:49:43.306 [http-nio-8080-exec-4] DEBUG org.springframework.web.servlet.DispatcherServlet - Exiting but response remains open for further handling

// ### 비동기 실행 처리 ###
04:49:46.320 [mvcTaskExecutor-1] DEBUG org.springframework.web.context.request.async.WebAsyncManager - Async result set, dispatch to /reservationQuery     

04:49:46.320 [http-nio-8080-exec-8] DEBUG org.springframework.web.servlet.DispatcherServlet - "ASYNC" dispatch for POST "/reservationQuery", parameters={masked}            
04:49:46.321 [http-nio-8080-exec-8] DEBUG org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter - Resume with async result ["reservationQuery"]
04:49:46.321 [http-nio-8080-exec-8] DEBUG org.springframework.web.servlet.view.JstlView - View name 'reservationQuery', model {reservations=[]}
04:49:46.321 [http-nio-8080-exec-8] DEBUG org.springframework.web.servlet.view.JstlView - Forwarding to [/WEB-INF/jsp/reservationQuery.jsp]
04:49:46.324 [http-nio-8080-exec-8] DEBUG org.springframework.web.servlet.DispatcherServlet - Exiting from "ASYNC" dispatch, status 200

```

* 원래 요청을 받았던... 스레드: `http-nio-8080-exec-4`
* 비동기 실행 처리 스레드:  `mvcTaskExecutor-1`
  * `http-nio-8080-exec-4` 는 다른일 가능 👍
* 비동기 실행 처리 스레드가 완료되어 남은일 처리하는 스레드: `http-nio-8080-exec-8`





## 의견

* 컨트롤러의 몇몇 메서드를 비동기로 만들긴 했지만 브라우저에서 사용할 때는 동기식과 마찬가지 아니냐? 는 생각을 할 수 있음.
  * Callable의 call의 메서드가 작업을 완료하고 결과를 반환할 때까지 클라이언트는 기다리기 때문 (클라이언트가 일반 폼전송을 했음.)
  * 그러나 Callable이 실행되는 동안 원래의 HTTP 요청을 처리하는 스레드는 다른 요청을 처리할 수 있는 장점이 있음.



## 기타

5-01-ii ~ iv의 변화가 둔순하게 처리를 DeferredResult, CompletableFuture, ListenableFuture 중 하나를 쓴 변화임.

예제 프로젝트를 따로 분리할 필요는 없고, 커밋 로그 링크를 적어두자.

방식만 달라서, Test 코드는 3가지 방식 동일하게 동작한다. 



### 레시피 5-01-ii 내용 포함

* ReservationQueryController#submitForm() 의 처리를 `DeferredResult<T>` 로 전환
* 커밋 로그
  * https://github.com/fp024/spring-5-recipes-study/commit/7d66ca3b8be68af6ba0723455b18f90919f9caf3#diff-40a6094713acd2ef8301597714d913197a280f41801d095f7ea330d6702a7eaf

### 레시피 5-01-iii 내용 포함

* ReservationQueryController#submitForm() 의 처리를 `CompletableFuture<T>` 로 전환
* 커밋 로그
  * https://github.com/fp024/spring-5-recipes-study/commit/73ec0b29192430ead6bfd3829c47dc1a2d33c7c1



### 레시피 5-01-iv 내용 포함

* ReservationQueryController#submitForm() 의 처리를 `ListenableFuture<T>` 로 전환
  * 비동기 설정 클래스의 mvcTaskExecutor()의 반환 타입도 AsyncListenableTaskExecutor로 변경
* 커밋 로그
  * 



## 정오표

* ...

