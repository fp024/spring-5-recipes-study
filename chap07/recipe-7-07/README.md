## 레시피 7-07 객체 보안 처리하기

> 이번에는 좀 복잡해보이는데... 일단 해보자.
>
> 도메인 객체마다 주체별로 접근 속성을 달리하기 

### 이번 레시피에서 확인해야할  내용

* ⬜ ACL 서비스 설정하기
  
* ⬜ 도메인 객체에 대한 ACL 관리하기
  
* ⬜ 표현식을 이용해 접근 통제 결정하기




## 진행

* ...




## 의견

* 



---

## 기타

### ✨ 이제 부터는 lombok을 좀 엄격하게 사용해보자...

* 예상하지 못한 내용으로 바뀐 경우가 있음
* `@Getter`, `@Setter`, `@ToSring`, `@NoArgsConstructor` 정도만 사용하고 다른 것은 꼭 사용해야한다면 Delombok 해서 변환 내용 확인하고 적용하기!



### 단순하게 BOM만 사용한다면  Gradle 5.0 이상 부터 지원되는 platform 키워드를 사용하자!

```groovy
implementation platform("org.springframework:spring-framework-bom:${springVersion}")
  implementation platform("org.springframework.security:spring-security-bom:${springSecurityVersion}")
```

* Spring Dependency Management 플러그인을 사용할 필요가 없음.





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

