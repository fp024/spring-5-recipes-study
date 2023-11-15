## 레시피 7-05-i 메서드 호출 보안하기 - 애너테이션을 붙여 메서드 보안하기

> 이 내용은 다시 최신 Spring Security로 진행하는데...
>
> Depreacted 된 내용을 잘 적어두자.

### 이번 레시피에서 확인해야할  내용

* ✅ 애너테이션을 붙여 메서드 보안하기

* ⬜ 애너테이션 + 표현식으로 메서드 보안하기

* ⬜ 애너테이션 + 표현식으로 거르기
  


## 진행

7-3 레시피에서 한 예제들과 큰 차이는 없는데...

단지 TodoServiceImpl 서비스 클래스의 메서드에다 `@Secured` 어노테이션을 붙여서,

서비스 메서드 단위 보안을 설정했다.



그런데 이렇게 TodoServiceImpl 에 메서드 단위 보안을 적용했을 때, 문제가 있었는데..

TodoInitializer 라는 클래스에서 TodoServiceImpl 의 save()를 호출해서 초기 Todos를 입력해주고 있었기 때문에...

임시 인증 설정이 필요했다.

#### TodoInitializer 의 setup()

```java
  @PostConstruct
  public void setup() {
    Authentication auth =
        new UsernamePasswordAuthenticationToken(
            "user", null, List.of(new SimpleGrantedAuthority("USER")));
    try {
      SecurityContextHolder.getContext().setAuthentication(auth);

      Todo todo = new Todo();
      todo.setOwner("marten@apressmedia.net");
      todo.setDescription("Finish Spring Recipes - Security Chapter");

      messageBoardService.save(todo);

      todo = new Todo();
      todo.setOwner("marten@apressmedia.net");
      todo.setDescription("Get Milk & Eggs");
      todo.setCompleted(true);
      messageBoardService.save(todo);

      todo = new Todo();
      todo.setOwner("marten@apressmedia.net");
      todo.setDescription("Call parents.");

      messageBoardService.save(todo);
    } finally {
      SecurityContextHolder.getContext().setAuthentication(null);
    }
  }
```

* SecurityContext에 USER권한을 가진 인증을 설정해 줘야했음.
* 기본 값 설정이 끝나면 바로 인증 정보를 null로 비움.
* UsernamePasswordAuthenticationToken를 만들 때, 패스워드는 설정할 필요가 없으므로 null

저자님 예제도 여전히 TodoInitializer가 적용되어있는데... 위처럼 설정이 안되어있어서...

```
Caused by: org.springframework.security.authentication.AuthenticationCredentialsNotFoundException: An Authentication object was not found in the SecurityContext
```

위와 같은 예외가 발생할 수 밖에 없다.



### Depreacted 된 내용

Spring Security 5.6 부터 `@EnableMethodSecurity`를 사용을 권장한다.

```java
@EnableGlobalMethodSecurity(securedEnabled = true) // 폐기됨
// ..
@EnableMethodSecurity(securedEnabled = true)
```

어노테이션 이름이 바뀌고 어노테이션의 기본 속성 값들이 바뀐듯.

* prePostEnabled가 새로운 어노테이션에서는 true가 기본값임.






## 의견

* ...



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

