## 레시피 7-04-ii 접근 통제 결정하기  - 표현식을 이용해 접근 통제 결정 - `레거시`

> ...
>

### 이번 레시피에서 확인해야할  내용

* ✔ 커스텀 거수기 작성

* ✅ 표현식을 이용해 접근 통제 결정하기
  
* ⬜ 스프링 빈을 표현식에 넣어 접근 통제 결정하기
  
  

## 진행

7-4는 우선 레거시 환경에서 먼저 진행한다.

폐기되거나 아예 사용할 수 없는 메서드들이 있다. 이 예제를 5.8환경에서 수행하기는 매우 힘들 것 같다.



### 폐기 목록

1. AffirmativeBased 대신 AuthorizationManager 사용
2. AuthenticatedVoter 대신 org.springframework.security.authorization.AuthorityAuthorizationManager  사용

저자님 예제도 코드를 보아도 뭔가 진행이되다 말은 것 같음.😅

아무래도 이전 버전으로 먼저 해보고, 최신 버전에 맞게 변경하는것이 나을 것 같다고는 생각은 했는데...

이전 버전으로 빨리 끝내보자..






## 의견

### 7-04-i 의 IpAddressVoter가 하는 일을 다음과 같은 표현식으로 구성할 수 있음.

```java
.access(
            "hasAuthority('ADMIN') and (hasIpAddress('127.0.0.1') or hasIpAddress('0:0:0:0:0:0:0:1'))")
```

* `hasAuthority('ADMIN')`의 경우는 그냥 넣어본 것이고, 그 옆의 IP 주소로 판단하는 부분이 IpAddressVoter가 하는 일과 같음.
* 로컬에서 확인시 내 윈도우 환경의 경우는 IPv6 주소(`0:0:0:0:0:0:0:1`)를 우선으로 요청자 IP를 체크하는 것 같다. 
* 현재의 스프링 시큐리티에는 표현식을 메서드로 제공해서 저렇게 쓸 필요는 없긴한데.. 







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