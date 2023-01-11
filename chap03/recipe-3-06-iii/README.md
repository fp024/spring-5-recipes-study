* ## 레시피 3-6-iii 이름으로 뷰 해석하기 3

  

  ### 이번 레시피에서 확인해야할  내용

  ##### 여러 리졸버를 이용해 뷰 해석하기

  * ViewResolverConfiguration#viewResolver()

    * viewResolver의 order(순번)메서드로 적용순서 지정

  * court-views.properties

  * 뷰의 존재 여부와 상관없이 InternalResourceViewResolver는 항상 뷰를 해석하므로 우선순위를 가장 낮게 할당해야함.

    그러면 차라리 아래와 같이 쓰는 것도 좋겠다.

    ```java
    viewResolver.setOrder(Ordered.LOWEST_PRECEDENCE);
    ```
  
  * 웹 애플리케이션 컨텍스트에 InternalResourceViewResolver가 구성되어있으면.. `redirect:`접두어를 붙이면 리다이렉트 뷰로 해석됨
  
  
  
  ## 진행
  
  * `ResourceBundleViewResolver`는 0번 우선순위 `InternalResourceViewResolver`로 설정
  
  * `/welcomeRedirect` 에대해서만 court-vew.properties에 설정을 남김
  
    ```properties
    welcomeRedirect.(class)=org.springframework.web.servlet.view.RedirectView
    welcomeRedirect.url=welcome
    ```
  
    
  
  
  


  ## 기타

  * ViewResolver 테스트 할 때.. MockMVC에서는 그냥 view설정이 없어도 그냥 되는 부분이 있나보다.. ?

    * `/reservationSummary.html?date=2022-01-01` URL에 대해 Mock MVC테스트는 성공했지만 실제 WAS실행 확인시에는 아래 예외가 발생했다.

      ```
      javax.servlet.ServletException: Could not resolve view with name 'reservationSummary' in servlet with name 'dispatcher'
      ```

      court-views.propertis에서 아래 내용을 누락했었었음.

      ```properties
      reservationSummary.(class)=org.springframework.web.servlet.view.JstlView
      reservationSummary.url=/WEB-INF/jsp/reservationSummary.jsp
      ```

      
