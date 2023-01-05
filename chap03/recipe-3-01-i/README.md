## 레시피 3-1-i 간단한 스프링 MVC 웹 애플리케이션 개발하기

> Jetty로 바로 실행할수 있게 좀 바꿨다...

* Gretty + Tomcat 9 추가해서 바로 실행할 수 있게 설정

  ```bash
  > gradle appRun
  ```

  ```groovy
  plugins { 
    id "org.gretty" version "${grettyVersion}"
    ...
  }
  
  ...
  // gradle appRun 으로 실행
  gretty {
    httpPort = 8080
    contextPath = "/"
    servletContainer = "tomcat9"
    inplaceMode = "hard"
  }
  ```

  

* LocalDateTIme을 바로 처리할 수 있도록 라이브러리 추가 및 적용

  ```groovy
  implementation "net.sargue:java-time-jsptags:${javaTimeJspTagsVersion}"
  ```

  * 사용

    ```jsp
    <%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
    ...
    <javatime:format value="${today}" pattern="yyyy-MM-dd"/>
    ```

    

## 테스트 URL

* http://localhost:8080/welcome
* http://localhost:8080/reservationQuery
  * `Tennis #1`,  `Tennis #2` 값 넣고 쿼리 버튼 누름.



## 기타사항

그런데... Gretty의 소스 변경시 자동 재시작이... Jetty에서는 오류 로그 출력되면서 잘 안되는 것 같음..  Tomcat에서는 잘됨. 그래서 Tomcat 9 설정으로 바꿨다.
