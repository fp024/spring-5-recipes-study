## 레시피 3-6-i 이름으로 뷰 해석하기 1

> 신이시여 힘을 주소서... 🙏

### 이번 레시피에서 확인해야할  내용

* ViewResolverConfiguration
* court-views.xml
* WelcomeControllerTest#testWelcomeRedirect()



🎇 InternalResourceViewResolver는 RequestDispatcher가 포워딩할 수 있는 내부 리소스(내부 JSP파일 또는 서블릿) 뷰만 해석할 수 있음.



## 진행

* welcomeRedirect 호출도 확인

  ```java
  @GetMapping("/welcomeRedirect")
  public void welcomeRedirect() {}
  ```

* court-views.xml 의 welcomeRedirect 정의 부분

  ```xml
    ...
    <bean id="welcomeRedirect" class="org.springframework.web.servlet.view.RedirectView">
      <property name="url" value="welcome" />
    </bean>
    ...
  ```

* ViewResolverConfiguration

  * xml만으로 잘되는지 궁금해서.. InternalResourceViewResolver는 주석으로 했는데, 동작은 이상없었다.




## 특이한점

위에 welcomeRedirect도 정의되어있길레 확인을 했었는데...

```
01:33:45.886 [http-nio-8080-exec-5] DEBUG org.springframework.web.servlet.DispatcherServlet - GET "/welcomeRedirect?language=en", parameters={masked}
01:33:45.887 [http-nio-8080-exec-5] DEBUG org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped to org.fp024.study.spring5recipes.court.web.WelcomeController#welcomeRedirect()
01:33:45.888 [http-nio-8080-exec-5] DEBUG org.springframework.web.servlet.view.RedirectView - View name 'welcomeRedirect', model {handlingTime=1}
01:33:45.888 [http-nio-8080-exec-5] DEBUG org.springframework.web.servlet.DispatcherServlet - Completed 302 FOUND
01:33:45.894 [http-nio-8080-exec-3] DEBUG org.springframework.web.servlet.DispatcherServlet - GET "/welcome?handlingTime=1", parameters={masked}
01:33:45.895 [http-nio-8080-exec-3] DEBUG org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped to org.fp024.study.spring5recipes.court.web.WelcomeController#welcome(Model)
01:33:46.157 [http-nio-8080-exec-3] DEBUG org.springframework.web.servlet.view.JstlView - View name 'welcome', model {today=2023-01-12T01:33:45.895761500, handlingTime=262}
01:33:46.157 [http-nio-8080-exec-3] DEBUG org.springframework.web.servlet.view.JstlView - Forwarding to [/WEB-INF/jsp/welcome.jsp]
01:33:46.161 [http-nio-8080-exec-3] DEBUG org.springframework.web.servlet.DispatcherServlet - Completed 200 OK
```

서버 켜서 실행해서 `/welcomeRedirect` 접근시 위의 실행 로그 처럼 302응답으로 welcome으로 리다렉트 되는 것이 로그 상에 나타났음.

그런데,  MVC 테스트에서는 200으로 나온다.. MVC 테스트에서는 그냥 포워딩이 되는 것 같은... 😅

```java
  @Test
  void testWelcomeRedirect() throws Exception {
    mockMvc
        .perform(get("/welcomeRedirect/?language=en")) //
        .andExpect(status().isOk()) // 테스트에서는 302 응답일 줄 알았는데... 200이다.
        .andExpect(view().name("welcomeRedirect"))
        .andExpect(cookie().value("language", "en"))
        .andDo(print());
  }
```

* MVC 테스트 로그

  ```
  ...
  01:35:16.999 [main] DEBUG org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped to org.fp024.study.spring5recipes.court.web.WelcomeController#welcomeRedirect()
  01:35:17.044 [main] DEBUG org.springframework.web.servlet.view.JstlView - View [JstlView], model {handlingTime=44}
  01:35:17.048 [main] DEBUG org.springframework.web.servlet.view.JstlView - Forwarding to [welcomeRedirect]
  
  MockHttpServletRequest:
        HTTP Method = GET
        Request URI = /welcomeRedirect/
         Parameters = {language=[en]}
            Headers = []
               Body = <no character encoding set>
      Session Attrs = {}
  
  Handler:
               Type = org.fp024.study.spring5recipes.court.web.WelcomeController
  ...
  ```

  

### XmlViewResolver는 5.3부터 Deprecated 됨

```
Deprecated as of 5.3, in favor of Spring's common view resolver variants and/or custom resolver implementations
```

Spring의 공통 뷰 리졸버 변형 및 사용자정의 리졸버 구현을 위해 5.3부터 사용되지 않음.

* 정확히 이해는 잘 안됨.... 천천히 생각하자~ 😅
