## 레시피 7-04-i 접근 통제 결정하기  - 커스텀 거수기 작성 - `현재`

> ...
>

### 이번 레시피에서 확인해야할  내용

* ✅ 커스텀 거수기 작성

* ⬜ 표현식을 이용해 접근 통제 결정하기
  
* ⬜ 스프링 빈을 표현식에 넣어 접근 통제 결정하기
  
  

## 진행

이번 예제는 스프링 시큐리티 5.8에서 수행하기가 애매하다.

폐기되거나 아예 사용할 수 없는 메서드들이 있다. 이 예제를 5.8환경에서 수행하기는 매우 힘들 것 같다.



### 폐기 목록

1. AffirmativeBased 대신 AuthorizationManager 사용
2. AuthenticatedVoter 대신 org.springframework.security.authorization.AuthorityAuthorizationManager  사용

저자님 예제도 코드를 보아도 뭔가 진행이되다 말은 것 같음.😅

아무래도 이전 버전으로 먼저 해보고, 최신 버전에 맞게 변경하는것이 나을 것 같은데...



### ✨이 프로젝트는 레시피 7-04를 현재 스프링 시큐리티 요구사항에 맞게 변경해야한다.








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


## 문서 아이콘 모음.
* ✔ ...
* ✅ ...
* ⬜ ...