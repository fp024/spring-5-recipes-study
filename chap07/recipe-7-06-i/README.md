## 레시피 7-06-i 뷰에서 보안 처리하기 - Thymeleaf 뷰

> 이미 Thymeleaf를 사용했기 때문에, 로그인 유저 표시하고, 조건부로 로그아웃 버튼 나타내는 것등은 이미 진행을 했다.

### 이번 레시피에서 확인해야할  내용



* ✅ Thymeleaf
  * ✅ 인증정보 표시하기

  * ✅ 뷰 콘텐트를 조건부 렌더링하기

* ⬜ JSP (JSP 하지 말까하다가.. 그냥 하자..😅)
  * ⬜ ...


## 진행

### 기본 설정

#### 디펜던시 추가

```groovy
  implementation "org.thymeleaf:thymeleaf-spring5:${thymeleafSpring5Version}" // ✨
  implementation "org.thymeleaf.extras:thymeleaf-extras-java8time:${thymeleafExtrasJava8timeVersion}"
implementation "org.thymeleaf.extras:thymeleaf-extras-springsecurity5:${thymeleafSpring5Version}" // ✨
```

* `thymeleaf-extras-springsecurity5` 를 추가

#### Java 설정

```java
  @Bean
  SpringTemplateEngine thymeleafTemplateEngine() {
    final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.addDialect(new Java8TimeDialect());
    templateEngine.addDialect(new SpringSecurityDialect()); // ✨
    templateEngine.setTemplateResolver(thymeleafTemplateResolver());
    return templateEngine;
  }
```

* `SpringSecurityDialect`를 등록

#### HTML 네임스페이스 설정

```html
<html lang="ko"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
```



###  인증정보 표시하기

```html
  <!--/* 목록 페이지가 항상 로그인을 요구해서 익명인 경우가 없음. */-->
  <th:block sec:authorize="isAuthenticated()">
    <h4 th:text="|To-dos for ${#authentication.name}|"></h4>
    <!--/* 보유한 권한 목록 표시 */-->
    <ul>
      <li th:each="authority : ${#authentication.authorities}" th:text="${authority.authority}"></li>
    </ul>
  </th:block>
```

* 로그인 유저 이름과 소유 권한을  표시함.



### 뷰 콘텐트를 조건부 렌더링 하기

```html
        <th:block sec:authorize="hasAuthority('ADMIN')">
        <form th:action="@{|/todos/${todo.id}|}" method="post" style="float: left;">
          <input type="hidden" name="_method" value="DELETE"/>
          <button class="ui mini red icon button"><i class="remove circle icon"></i></button>
        </form>
        </th:block>
```

* Todo의 삭제 버튼은 `ADMIN` 권한을 가졌을 때만 노출되게 하였다.




## 의견

* 다음 예제는 이 예제를 JSP로 바꿔서 만들어보자.. 😂



---

## 기타

* ...

  

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

