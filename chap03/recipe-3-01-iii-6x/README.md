## 레시피 3-1-ii 간단한 스프링 MVC 웹 애플리케이션 개발하기 3 - Spring 6

> Spring 6 기반으로 설정을 추가해보았음..
>
> * Spring 6 + Servlet 6.0 + Tomcat 10.1.x
>
> 그런데 특이한 점이... Spring 5일 때는 
>
> `http://localhost:8080/welcome` 이나 `http://localhost:8080/welcome/` 이 둘다 welcome 페이지로 이동했는데..
>
> 6.0에서는 `http://localhost:8080/welcome/`에 대해서는 404가 발생한다. 그런데.. 테스트 코드에서는 성공한다는? 😓
>
> Spring Security에서 MVC Matcher가 이전 버전과 다르게 동작하는 문제도 이와 관련된 문제일 것 같은데...
>
> 뭔가 기본설정이 바뀌었나? 천천히 알아보자..
>
> 그런데 나도 혼동이 되는게
>
> `http://localhost:8080/welcome/` 도 welcome으로 정상적으로 가는게 올바른 것인가? 그점이 해깔림.

