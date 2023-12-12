## 레시피 7-07 객체 보안 처리하기 - jakrata

> Spring 6.1 + Spring Seucirty 6.2.1-SNAPSHOT 에서 확인해보자.
>
> 

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



그런데... 다음과 같이 현시점 완전 최신 버전들로 올리면.... 문제가 생김. 😅

```groovy
ext {
  springVersion = "6.1.1"
  thymeleafSpringVersion = "${thymeleafSpring5Version}" // 5, 6 버전 동일
  springSecurityVersion = '6.2.1-SNAPSHOT'
  jakartaElVersion = '5.0.0-M1'
}
```



다음과 같은 에외가 발생함.

```
java.lang.IllegalArgumentException: identifier required
	at org.springframework.util.Assert.notNull(Assert.java:172)
	at org.springframework.security.acls.domain.ObjectIdentityImpl.<init>(ObjectIdentityImpl.java:42)
	at org.springframework.security.acls.domain.ObjectIdentityRetrievalStrategyImpl.createObjectIdentity(ObjectIdentityRetrievalStrategyImpl.java:41)
	...
```








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

