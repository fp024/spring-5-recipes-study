## 레시피 7-04-iii 접근 통제 결정하기  - 스프링 빈을 표현식에 넣어 접근 통제 결정하기 - `레거시`

> ...
>

### 이번 레시피에서 확인해야할  내용

* ✔ 커스텀 거수기 작성

* ✔ 표현식을 이용해 접근 통제 결정하기
  
* ✅ 스프링 빈을 표현식에 넣어 접근 통제 결정하기
  
  

## 진행

7-4는 우선 레거시 환경에서 먼저 진행한다.

폐기되거나 아예 사용할 수 없는 메서드들이 있다. 이 예제를 5.8환경에서 수행하기는 매우 힘들 것 같다.



### 폐기 목록

* ...






## 의견

이번에도 간단했다.

빈에 조건을 판별하는 메서드 하나를 넣어두고, 설정 클래스에서 `@빈이름.메서드이름(authentication)`으로 호출하는데 꽤 간편해보였다.

**조건 판별 메서드를 포함하는 빈 클래스**

```java
public class AccessChecker {
  public boolean hasLocalAccess(Authentication authentication) {
    boolean access = false;

    if (authentication.getDetails() instanceof WebAuthenticationDetails details) {
      val address = details.getRemoteAddress();
      return address.equals("127.0.0.1") || address.equals("0:0:0:0:0:0:0:1");
    }
    return access;
  }
}
```

**설정 부분**

```java
.access("hasAuthority('ADMIN') and @accessChecker.hasLocalAccess(authentication)")
```





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