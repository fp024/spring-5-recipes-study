## 레시피 5-03 비동기 인터셉터

> 비동기 컨트롤러 메서드 실행 시간을 재는 인터셉터를 다룬다.
> 

### 이번 레시피에서 확인해야할  내용

* 비동기 인터셉터 동작확인

  

## 진행

#### afterConcurrentHandlingStarted() 메서드 궁금한 점.

비동기 요청이 들어오면 바로 실행되는데...  이미 request에 가지고 있는 START_TIME을 왜 불러와서 시간 계산에 포함하는지 모르겠다. 

직전 예제에서 SseEmitter 메서드 호출 하면  바로 아래 내용이 실행됨.

```
...
14:44:30.561 [http-nio-8080-exec-3] DEBUG org.springframework.web.servlet.DispatcherServlet - GET "/reservationQuery?courtName=Tennis%20%231", parameters={masked}                                                       
14:44:30.561 [http-nio-8080-exec-3] DEBUG org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped to org.fp024.study.spring5recipes.court.web.ReservationQueryController#find(String)
14:44:30.561 [http-nio-8080-exec-3] DEBUG org.springframework.web.context.request.async.WebAsyncManager - Started async request                                                                                          
[afterConcurrentHandlingStarted] Request-Processing-Time: 0ms.
[afterConcurrentHandlingStarted] Request-Processing-Thread: http-nio-8080-exec-3
14:44:30.561 [http-nio-8080-exec-3] DEBUG org.springframework.web.servlet.DispatcherServlet - Exiting but response remains open for further handling
14:44:34.482 [mvcTaskExecutor-1] DEBUG org.springframework.web.context.request.async.WebAsyncManager - Async result set, dispatch to /reservationQuery                                 
14:44:34.482 [http-nio-8080-exec-5] DEBUG org.springframework.web.servlet.DispatcherServlet - "ASYNC" dispatch for GET "/reservationQuery?courtName=Tennis%20%231", parameters={masked}
14:44:34.483 [http-nio-8080-exec-5] DEBUG org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter - Resume with async result []                             
[postHandle] Response-Processing-Time: 3922ms.
[postHandle] Response-Processing-Thread: http-nio-8080-exec-5
14:44:34.483 [http-nio-8080-exec-5] DEBUG org.springframework.web.servlet.DispatcherServlet - Exiting from "ASYNC" dispatch, status 200
...
```

* 예외 로그를 보면 afterConcurrentHandlingStarted() 메서드 내에서는 아직 비동기 용도로 새로 만들어진 스레드는 아님.
* 이후에 `mvcTaskExecutor-1` 비동기 스레드가 실행된 것이 보임.
* 마지막으로 모두 완료되면 postHandle 수행된 것도 확인됨. 이때 스래드는 응답을 위한 다른 HTTP 스레드가 사용된 것이 보임.



#### AsyncHandlerInterceptor 를 구현한 인터셉터라도 비동기만 처리하는 것은 아님  동기식은 그대로 preHandle, postHandle에서 처리됨

```
15:12:12.894 [http-nio-8080-exec-5] DEBUG org.springframework.web.servlet.DispatcherServlet - GET "/welcome", parameters={}
15:12:12.895 [http-nio-8080-exec-5] DEBUG org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped to org.fp024.study.spring5recipes.court.web.WelcomeController#welcome(Model)
[postHandle] Response-Processing-Time: 4872ms.                                                                                                                               
[postHandle] Response-Processing-Thread: http-nio-8080-exec-5                                                                                                                
15:12:17.771 [http-nio-8080-exec-5] DEBUG org.springframework.web.servlet.view.JstlView - View name 'welcome', model {today=2023-10-06T15:12:17.764564900, handlingTime=4872}
15:12:17.773 [http-nio-8080-exec-5] DEBUG org.springframework.web.servlet.view.JstlView - Forwarding to [/WEB-INF/jsp/welcome.jsp]
15:12:18.551 [http-nio-8080-exec-5] DEBUG org.springframework.web.servlet.DispatcherServlet - Completed 200 OK

```

* preHandle 이후 컨트롤러 메서드 처리이후 postHandle 가 실행된 것을 볼 수 있음.



## 의견

* 이번예제는 인터셉터 동작만 확인하는 것이여서 특별한 부분은 별로 없었다.

* 단지... 비동기 REST요청을 할 때.. postHandle()의 ModelAndView가 null일 수 있으니...

  ```java
  if (modelAndView != null) {
    modelAndView.addObject("handlingTime", responseProcessingTime);
  }
  ```

  이런 처리를 해주면 될 것 같다.



## 기타

* ...



## 정오표

* 294쪽
  * preHandle(), postHandle() 두 메서드는 각각 핸들러가 요청을 처리하기 **이전에**
    * preHandle(), postHandle() 두 메서드는 각각 핸들러가 요청을 처리하기 **이전, 이후에**
    * 책읽다보니 둘다 이전이라는 표현같아서 뭔가 이상했음..😅




---

## JavaDocs

### HandlerInterceptor 인터페이스

```java
default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
```

> 핸들러 실행 전의 인터셉션 지점입니다. `HandlerMapping`이 적절한 핸들러 객체를 결정한 후 `HandlerAdapter`가 핸들러를 호출하기 전에 호출됩니다.
> `DispatcherServlet`은 핸들러 자체를 마지막에 두고 여러 개의 인터셉터로 구성된 실행 체인에서 핸들러를 처리합니다. 이 메서드를 사용하면 각 인터셉터가 실행 체인을 중단하여 일반적으로 HTTP 오류를 보내거나 사용자 정의 응답을 작성할 수 있습니다.
> *참고:* 비동기 요청 처리에는 특별한 고려 사항이 적용됩니다. 자세한 내용은 `AsyncHandlerInterceptor`를 참조하세요.
> 기본 구현은 `true`을 반환합니다.
>
> * 매개변수
>   * request – 현재 HTTP 요청
>   * response – 현재 HTTP 응답
>   * handler – 타입 및/또는 인스턴스 평가를 위해 실행할 핸들러를 선택합니다.
> * 반환
>   * 실행 체인이 다음 인터셉터 또는 핸들러 자체로 진행해야 하는 경우 `true`입니다. 그렇지 않으면 `DispatcherServlet`은 이 인터셉터가 이미 응답 자체를 처리한 것으로 가정합니다.
> * 던지기
>   * Exception - 오류 발생시

```java
default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception 
```

> 핸들러가 성공적으로 실행된 후의 인터셉션 지점입니다. `HandlerAdapter`가 실제로 핸들러를 호출한 후 `DispatcherServlet`이 뷰를 렌더링하기 전에 호출됩니다. 지정된 `ModelAndView`를 통해 뷰에 추가 모델 객체를 노출할 수 있습니다.
> `DispatcherServlet`은 처리기 자체를 마지막에 두고 여러 개의 인터셉터로 구성된 실행 체인에서 처리기를 처리합니다. 이 메서드를 사용하면 각 인터셉터가 실행을 사후 처리하여 실행 체인의 역순으로 적용될 수 있습니다.
> *참고:* 비동기 요청 처리에는 특별한 고려 사항이 적용됩니다. 자세한 내용은 비동기 핸들러 인터셉터를 참조하세요.
> 기본 구현은 비어 있습니다.
>
> * 매개변수
>   * request – 현재 HTTP 요청
>   * response – 현재 HTTP 응답
>   * handler – 타입 및/또는 인스턴스 검사를 위해 비동기 실행을 시작한 핸들러(또는 핸들러 메서드) - 핸들러가 반환한 ModelAndView(null일 수도 있음).
> * 던지기
>   * Exception - 오류 발생시

```java
default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception
```

> **요청 처리가 완료된 후, 즉 뷰를 렌더링한 후 호출됩니다.** 핸들러 실행의 모든 결과에 대해 호출되므로 **적절한 리소스 정리가 가능합니다.**
> 참고: 이 인터셉터의 `preHandle` 메서드가 성공적으로 완료되어 `true`를 반환한 경우에만 호출됩니다!
> `postHandle` 메서드와 마찬가지로 이 메서드는 체인의 각 인터셉터에서 역순으로 호출되므로 첫 번째 인터셉터가 마지막에 호출됩니다.
> *참고:* 비동기 요청 처리에는 특별한 고려 사항이 적용됩니다. 자세한 내용은 비동기 핸들러 인터셉터를 참조하세요.
> 기본 구현은 비어 있습니다.
>
> * 매개변수:
>   * request – 현재 HTTP 요청
>   * response – 현재 HTTP 응답
>   * handler – 타입 및/또는 인스턴스 검사를 위해 비동기 실행을 시작한 핸들러(또는 핸들러 메서드) 
>   * ex - 핸들러 실행 시 던져진 예외(있는 경우); 예외 해결자를 통해 처리된 예외는 포함되지 않습니다.
> * 던지기:
>   * Exception – 오류 발생시



### AsyncHandlerInterceptor 인터페이스

> 비동기 요청 처리가 시작된 후 호출되는 콜백 메서드가 있는 `HandlerInterceptor`를 확장합니다.
> 핸들러가 비동기 요청을 시작하면, 요청 처리 결과(예: `ModelAndView`)가 아직 준비되지 않았을 가능성이 높고 다른 스레드에서 동시에 생성될 것이므로, `DispatcherServlet`은 동기 요청에 대해 일반적으로 수행하는 것처럼 `postHandle` 및 `afterCompletion`을 호출하지 않고 종료됩니다.
>
> 이러한 시나리오에서는 대신 `afterConcurrentHandlingStarted`가 호출되어 구현이 스레드를 서블릿 컨테이너에 릴리스하기 전에 스레드 바인딩 속성 정리와 같은 작업을 수행할 수 있습니다.
>
> 비동기 처리가 완료되면 추가 처리를 위해 요청이 컨테이너로 전달됩니다. 이 단계에서 `DispatcherServlet`은 `preHandle`, `postHandle` 및 `afterCompletion`을 호출합니다. 비동기 처리가 완료된 후 초기 요청과 후속 디스패치를 구별하기 위해 인터셉터는 `javax.servlet.ServletRequest`의 `javax.servlet.DispatcherType`이 `"REQUEST"` 또는 `"ASYNC"`인지 확인할 수 있습니다.
> 비동기 요청이 시간 초과되거나 네트워크 오류로 완료되면 `HandlerInterceptor` 구현이 작업을 수행해야 할 수도 있습니다. 이러한 경우 서블릿 컨테이너는 디스패치되지 않으므로 `postHandle` 및 `afterCompletion` 메소드가 호출되지 않습니다. 대신 인터셉터는 `WebAsyncManager`의 `RegisterCallbackInterceptor` 및 `RegisterDeferredResultInterceptor` 메서드를 통해 비동기 요청을 추적하도록 등록할 수 있습니다. 이는 비동기 요청 처리 시작 여부에 관계없이 `preHandle`의 모든 요청에 대해 사전에 수행될 수 있습니다.

```java
default void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
```

> 핸들러가 동시에 실행 중일 때 `postHandle`과 `afterCompletion` 대신 호출됩니다.
> 구현은 제공된 요청과 응답을 사용할 수 있지만 핸들러의 동시 실행과 충돌하는 방식으로 수정하지 않아야 합니다. 이 방법의 일반적인 사용은 스레드 로컬 변수를 정리하는 것입니다.
>
> * 매개변수
>   * request – 현재 요청
>   * response – 현재 응답
>   * handler – 타입 및/또는 인스턴스 검사를 위해 비동기 실행을 시작한 핸들러(또는 핸들러 메서드)를 반환합니다.



그런데 좀.. 이해가..😂

동작 로그를 봤을 때는 Ajax Fetch 호출 하자마자 afterConcurrentHandlingStarted가 바로 실행되고,

줄줄이 비동기 응답 받다가... 완료될때, postHandle 가 호출 되었는데... 그런데.. preHandle, afterCompletion는 로그를 설정해두지 않아서... 음.. 로그를 찍어보고 해보자..

위의 JavaDoc 내용대로라면 아래 순서대로임

1. 비동기 요청 실행
2. afterConcurrentHandlingStarted() 호출
3. 비동기 처리 완료되면...
4. preHandle 호출
5. postHandle 호출 
6. afterCompletion  호출

```
21:50:27.862 [http-nio-8080-exec-7] INFO  org.fp024.study.spring5recipes.court.web.MeasurementInterceptor - ### preHandle ###
21:50:27.862 [http-nio-8080-exec-7] DEBUG org.springframework.web.context.request.async.WebAsyncManager - Started async request
[afterConcurrentHandlingStarted] Request-Processing-Time: 0ms.
[afterConcurrentHandlingStarted] Request-Processing-Thread: http-nio-8080-exec-7
21:50:27.862 [http-nio-8080-exec-7] DEBUG org.springframework.web.servlet.DispatcherServlet - Exiting but response remains open for further handling
21:50:31.406 [mvcTaskExecutor-1] DEBUG org.springframework.web.context.request.async.WebAsyncManager - Async result set, dispatch to /reservationQuery                                 
21:50:31.407 [http-nio-8080-exec-8] DEBUG org.springframework.web.servlet.DispatcherServlet - "ASYNC" dispatch for GET "/reservationQuery?courtName=Tennis%20%231", parameters={masked}
21:50:31.407 [http-nio-8080-exec-8] INFO  org.fp024.study.spring5recipes.court.web.MeasurementInterceptor - ### preHandle ###                                                          
21:50:31.407 [http-nio-8080-exec-8] DEBUG org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter - Resume with async result []                             
21:50:31.408 [http-nio-8080-exec-8] INFO  org.fp024.study.spring5recipes.court.web.MeasurementInterceptor - ### postHandle ###                                                         
[postHandle] Response-Processing-Time: 3546ms.                                                                                                                                         
[postHandle] Response-Processing-Thread: http-nio-8080-exec-8                                                                                                                          
21:50:31.408 [http-nio-8080-exec-8] INFO  org.fp024.study.spring5recipes.court.web.MeasurementInterceptor - ### afterCompletion ###                                                    
21:50:31.408 [http-nio-8080-exec-8] DEBUG org.springframework.web.servlet.DispatcherServlet - Exiting from "ASYNC" dispatch, status 200       
...
```

그런데 처음 요청할 때.. preHandle한번은 실행되는 것 같음.. 이후 순서가 아래와 같았음.

1. afterConcurrentHandlingStarted 호출 
2. preHandle
3. postHandle
4. afterCompletion

