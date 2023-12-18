## 레시피 7-07 객체 보안 처리하기 - jakrata

> Spring 6.1.x + Spring Seucirty 6.2.x 에서 확인해봤는데, 오류가 난다.
>
> Spring 6.0.x + Spring Security 6.1.x까지는 괜찮음.

### 이번 레시피에서 확인해야할  내용

* ✅ ACL 서비스 설정하기
  
* ✅ 도메인 객체에 대한 ACL 관리하기
  
* ✅ 표현식을 이용해 접근 통제 결정하기

* ⬜ AffirmativeBased 사용한 부분  AuthorizationManager 사용으로 전환해보기 😅



## 진행

프로젝트를 최신 환경으로도 전환시켜보고 싶어서 바꿔봤는데...

Spring 6.0.13 + Spring Security 6.1.6-SNAPSHOT 까지는 동작 및 테스트에 문제가 없음.

```groovy
ext {
  springVersion = "6.0.13"
  thymeleafSpringVersion = "${thymeleafSpring5Version}" // 5, 6 버전 동일
  springSecurityVersion = '6.1.6-SNAPSHOT'
  jakartaElVersion = '5.0.0-M1'
}
```

* 💡 Spring 버전은 Spring Security의 의존성에 의해 6.0.14로 됨.




## 의견

* ...





---

## 기타

## `Spring 6.1.2` + `Spring Seucirty 6.2.1-SNAPSHOT`으로 버전업시 오류

Spring 6.1.2 + Spring Security 6.2.1-SNAPSHOT으로 버전을 올라면 아래와 같은 오류가 발생함.

```
java.lang.IllegalArgumentException: identifier required
	at org.springframework.util.Assert.notNull(Assert.java:172)
	at org.springframework.security.acls.domain.ObjectIdentityImpl.<init>(ObjectIdentityImpl.java:42)
	at org.springframework.security.acls.domain.ObjectIdentityRetrievalStrategyImpl.createObjectIdentity(ObjectIdentityRetrievalStrategyImpl.java:41)
	...
```

디버깅을 해보니 `ObjectIdentityImpl` 의 생성자에서 identifier가 null로 넘어와서 오류가 남

```java
	public ObjectIdentityImpl(String type, Serializable identifier) {
		Assert.hasText(type, "Type required");
		Assert.notNull(identifier, "identifier required"); // ✨ identifier가 null로 넘어와서 오류가 남
		this.identifier = identifier;
		this.type = type;
	}
```

다음과 같이 사용한 부분에서

```java
  @PreAuthorize(
      "hasPermission(#id, 'org.fp024.study.spring5recipes.board.domain.Todo', 'write')") // ✨
  public void complete(long id) {
```

id가 처음부터 제대로 넘어가지 않아서 생긴 문제였음.

그래서 마이그레이션 가이드를 찾아봤는데, 내용이 있었다.

* https://docs.spring.io/spring-security/reference/migration/authorization.html

### -parameters로 컴파일하기 

Spring Framework 6.1에서는 `LocalVariableTableParameterNameDiscoverer`를 제거합니다. 이는 `@PreAuthorize` 및 기타 메소드 보안 어노테이션이 매개변수 이름을 처리하는 방식에 영향을 미칩니다. 예를 들어, 매개변수 이름을 사용하는 메소드 보안 어노테이션을 사용하고 있다면, 런타임에서 매개변수 이름을 사용할 수 있도록 `-parameters`로 컴파일해야 합니다.  

#### id 매개변수 이름을 사용하는 메소드 보안 어노테이션

```java
@PreAuthorize("@authz.checkPermission(#id, authentication)")
public void doSomething(Long id) {
    // ...
}
```

이에 대한 자세한 정보는 [Spring Framework 6.1](https://github.com/spring-projects/spring-framework/wiki/Upgrading-to-Spring-Framework-6.x#core-container)로 업그레이드하는 페이지를 참조하십시오.

> 그러면 Gradle 빌드 옵션에 `-parameters`를 추가해야할 것 같다.



#### build.gradle에 다음 내용 추가

```groovy
tasks.withType(JavaCompile) {
  options.compilerArgs << '-parameters'
}
```

이후 테스트가 성공해서 버전을 Spring 6.1.2 + Spring Seucirty 6.2.1-SNAPSHOT으로 올리기로 했다. 👍

* 2023-12-19에 6.2.1 정식버전 나와서 테스트 했는데, 잘 동작했다.





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

