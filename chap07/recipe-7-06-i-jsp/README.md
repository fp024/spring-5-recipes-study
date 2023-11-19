## 레시피 7-06-i-jsp 뷰에서 보안 처리하기 - JSP 뷰

> 앞의 예제에서는 Thymeleaf로 예제를 작성했으니, JSP 뷰로 진행해보자! 💡

### 이번 레시피에서 확인해야할  내용

* ✔ Thymeleaf
  * ✔ 인증정보 표시하기

  * ✔ 뷰 콘텐트를 조건부 렌더링하기

* ✅ JSP (JSP 하지 말까하다가.. 그냥 하자..😅)
  * ✅ ...



## 진행

### 기본 설정

#### 디펜던시 추가

```groovy
implementation "org.springframework.security:spring-security-taglibs:${springSecurityVersion}"
runtimeOnly "javax.servlet:jstl:${javaxJstlVersion}"
```

* JSP용 스프링 시큐리티 테그 라이브러리를 사용해야하므로 위의 디펜던시 추가

  

#### Java 설정

```java
  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    registry.jsp("/WEB-INF/jsp/", ".jsp");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("/*.html", "/favicon.ico") //
        .addResourceLocations("/");

    registry
        .addResourceHandler("/resources/**") //
        .addResourceLocations("/resources/");

    registry
        .addResourceHandler("/webjars/**")
        .addResourceLocations("/webjars/")
        .resourceChain(false);
  }
```

* Thymeleaf 설정은 제거
* 내부에서 InternalResourceViewResolver를 설정해주는 jsp() 메서드 사용
  

#### JSP 페이지에서 JSP 테그라이브러리 선언

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 컨텍스트패스 기준으로 URL 설정할 때... c:url 사용보다는 아래가 편해서 미리 설정함 --%>
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}"/>
```



###  인증정보 표시하기

```html
  <!--/* 목록 페이지가 항상 로그인을 요구해서 익명인 경우가 없음. */-->
  <sec:authorize access="isAuthenticated()">
    <h4>To-dos for <sec:authentication property="name"/></h4>
    <!--/* 보유한 권한 목록 표시 */-->
    <sec:authentication property="authorities" var="authorities"/>
    <ul>
      <c:forEach items="${authorities}" var="authority">
        <li>${authority.authority}</li>
      </c:forEach>
    </ul>
  </sec:authorize>
```

* 로그인 유저 이름과 소유 권한을  표시함.



### 뷰 콘텐트를 조건부 렌더링 하기

```html
<sec:authorize access="hasAuthority('ADMIN')">
  <form action="${contextPath}/todos/${todo.id}" method="post" style="float: left;">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="_method" value="DELETE"/>
    <button class="ui mini red icon button"><i class="remove circle icon"></i></button>
  </form>
</sec:authorize>
```

* Todo의 삭제 버튼은 `ADMIN` 권한을 가졌을 때만 노출되게 하였다.




## 의견

* Thymeleaf를 다시 JSP로 변환하려니 상당히 귀찮긴 했지만... 하고나니 괜찮다..😅



---

## 기타

### 로그인 유저 정보를 컨트롤러에서 받아올 때...

```java
  @PostMapping
  public String newMessage(
      @ModelAttribute @Valid Todo todo, BindingResult errors, Authentication authentication) {

    if (errors.hasErrors()) {
      return "todo-create";
    }

    todo.setOwner(authentication.getName());
    todoService.save(todo);
    return "redirect:/todos";
  }
```

* Authentication을 메서드 파라미터로 받아오면 되었다.
  다른 방법으로는 SecurityContextHolder로 받아와도 될 듯..

  ```java
  SecurityContextHolder.getContext().getAuthentication().getName();
  ```

  



## 정오표

* ...
  


---

## JavaDocs

* ...



---

### 문서 사용 아이콘

* ✅
* ✔
* ⬜
* ...

