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



## 해결

> Spring 6 MVC에서는 URL의 후행 슬레시를 자동처리해주지 않는다고 하고 설정도 Deprecated되었음.
>
> * https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide#web-application-changes
>
> ```java
> @Configuration
> public class WebMvcConfig implements WebMvcConfigurer {
>   @Override
>   public void configurePathMatch(PathMatchConfigurer configurer) {
>     // 메서드가 Spring 6.0 부터 Deprecated 되었으나 추가해주면 후행 슬레시 처리 동작은 됨.
>     configurer.setUseTrailingSlashMatch(true);
>   }
> }
> ```
>
> 이 설정을 쓰기보다는 다른 방법으로 명시적으로 redirects/rewrites 를 구성하라고 한다.
> RequestMapping에 /가 끝에 붙은 URL을 하나 더 추가하라고 한다. ㅎㅎ