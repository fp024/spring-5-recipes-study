## 레시피 5-04-i 웹소켓

> 실시간 적이여서 이번 예제는 재미있을 것 같은데... 😄👍
> 

### 이번 레시피에서 확인해야할  내용

* 서버/클라인트가 웹에서 서로 양방향 통신하는 방법

  

## 진행

#### WAS에 어떻게 배포?

저자님 파일을 보니 Tomcat 8.5 도커 컨테이너에다 배포하시는 것 같은데... 나는 Gretty + Tomcat 9.0 환경에서 잘되면 도커로 따로 진행할 필요는 없을 것 같다.

그런데 역자님은 왠지 Gretty + Jetty 9.4로 테스트 하신듯.


#### 디펜던시 추가

```groovy
implementation "org.springframework:spring-websocket:${springVersion}"
```





## 의견

* ...
  


## 기타

#### 메시지 폼 페이지 디자인에, Semantic-UI가 사용되었는데, 이부분도 webjars를 사용할 수 있었다.

* html 사용

  ```html
  <link type="text/css" rel="stylesheet" href="webjars/Semantic-UI/semantic.min.css" />
  ```

* build.gradle

  ```groovy
  implementation "org.webjars:Semantic-UI:${semanticUiVersion}"
  ```



#### `[Disconnect]` 버튼을 누르면 다음 예외가 발생함.

```
16:36:44.528 [http-nio-8080-exec-10] WARN  org.springframework.web.socket.handler.ExceptionWebSocketHandlerDecorator - Unhandled exception after connection closed for ExceptionWebSocketHandlerDecorator [delegate=Logging
WebSocketHandlerDecorator [delegate=org.fp024.study.spring5recipes.reactive.court.EchoHandler@6f538dd7]]
java.lang.IllegalStateException: Message will not be sent because the WebSocket session has been closed
...
        at org.springframework.web.socket.adapter.standard.StandardWebSocketSession.sendTextMessage(StandardWebSocketSession.java:215) ~[spring-websocket-5.3.30.jar:5.3.30]
        at org.springframework.web.socket.adapter.AbstractWebSocketSession.sendMessage(AbstractWebSocketSession.java:108) ~[spring-websocket-5.3.30.jar:5.3.30]
        at org.fp024.study.spring5recipes.reactive.court.EchoHandler.afterConnectionClosed(EchoHandler.java:17) ~[main/:?]
...
```

* 연결이 끊기 처리가 되긴 한것 같은데... 서버측 예외가 발생함. 😅

관련 부분을 확인해봤는데... 연결이 끊긴 상태에서 메시지를 보내려하는 코드가 있음. 다음 처럼 바꿈.

```java
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    // 커넥션이 닫힌 상태여서 메시지를 보낼 수 없음.
    // session.sendMessage(new TextMessage("CONNECTION CLOSED"));

    // 그런데 특이한 점은 session은 아직 열려있음.
    LOGGER.info("### session is open: {} ###", session.isOpen());
    LOGGER.info("### CONNECTION CLOSED ###");
  }
```

이후 서버 로그는 아래와 같음.

```
17:10:42.882 [http-nio-8080-exec-5] INFO  org.fp024.study.spring5recipes.reactive.court.EchoHandler - ### session is open: true ###
17:10:42.882 [http-nio-8080-exec-5] INFO  org.fp024.study.spring5recipes.reactive.court.EchoHandler - ### CONNECTION CLOSED ###
```

> `afterConnectionClosed` 메서드는 웹소켓 세션이 종료된 후에 호출되는 메서드입니다. 그러나 이 메서드가 호출되었다고 해서 세션이 완전히 닫힌 것은 아닙니다. [이 메서드는 연결이 끊어진 시점에서 호출되며, 이 시점에서 세션 객체의 상태는 여전히 열려있을 수 있습니다](https://stackoverflow.com/questions/35017170/why-cant-i-get-call-function-afterconnectionclosed-in-my-textwebsockethandler). 따라서 `session.isOpen()`이 `true`를 반환하는 것은 예상 가능한 동작입니다. 이후 시스템에 의해 세션 객체가 완전히 닫힐 것입니다. [이것이 `TextWebSocketHandler` 인터페이스의 설계된 동작입니다](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/socket/handler/TextWebSocketHandler.html).
>
> 그러므로, `afterConnectionClosed` 메서드에서 `session.isOpen()`이 `true`를 반환하는 것은 정상적인 동작입니다. 이 메서드는 연결이 끊어진 직후에 호출되므로, 이 시점에서 세션은 아직 완전히 닫히지 않았을 수 있습니다. 그러나, 세션은 곧 완전히 닫힐 것입니다.



### 테스트

#### 💡 TODO: 테스트 방법은 아직은 잘 모르겠음.

* https://github.com/rstoyanchev/spring-websocket-portfolio/tree/main/src/test



## 정오표

* ...

  


---

## JavaDocs

```java
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }
```

> 처리되지 않은 요청을 서블릿 컨테이너의 "default" 서블릿으로 전달하여 위임하도록 핸들러를 구성합니다. 일반적인 사용 사례는 DispatcherServlet이 "/"에 매핑되어 정적 리소스에 대한 서블릿 컨테이너의 기본 처리를 재정의하는 경우입니다.
>
> #### enable()
>
> "default" 서블릿으로 포워딩을 활성화합니다.
> 이 메서드를 사용하면 DefaultServletHttpRequestHandler가 "default" 서블릿 이름을 자동 감지하려고 시도합니다. 또는 enable(String)을 통해 기본 서블릿의 이름을 지정할 수 있습니다.
