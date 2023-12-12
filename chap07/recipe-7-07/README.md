## 레시피 7-07 객체 보안 처리하기

> 이번에는 좀 복잡해보이는데... 일단 해보자.
>
> 도메인 객체마다 주체별로 접근 속성을 달리하기 
>
> * ✨ Spring Security 5.8.9 기준으로 검색이 안되서 기능이 없어진 것 인가 했는데... `spring-security-acl`를 추가해주니 Deprectaed된 클래스 없이 다 있었다.
>
>   

### 이번 레시피에서 확인해야할  내용

* ⬜ ACL 서비스 설정하기
  
* ⬜ 도메인 객체에 대한 ACL 관리하기
  
* ⬜ 표현식을 이용해 접근 통제 결정하기



## 진행

#### 라이브러리 추가

* 다음 라이브러리 추가가 필요하다.

  ```groovy
  implementation "org.springframework.security:spring-security-acl"
  ```
  
  * 2.0.0 버전 때부터 있던 오래전 부터 있었던 라이브러리
    * https://mvnrepository.com/artifact/org.springframework.security/spring-security-acl



#### DB는 Embedded를 쓰지말고, 독립실행형으로 바꾸자..

* ACL 테이블에 뭐가 저장되는지 확인을 위해서... HSQLDB URL Only 접근으로 확인해야겠다.



### Depreacted된 방식 전환

```java
  // 😈
  @Bean
  public AffirmativeBased accessDecisionManager(AclEntryVoter aclEntryVoter) {
    List<AccessDecisionVoter<?>> decisionVoters =
        Arrays.asList(new WebExpressionVoter(), aclEntryVoter);
    return new AffirmativeBased(decisionVoters);
  }
```

* 5.8.8에서는 `Depreacted` 된 상태 이지만,  5.7.11에서는 아님.

* [Authorization :: Spring Security](https://docs.spring.io/spring-security/reference/servlet/authorization/index.html) 왠지 여기있는 내용을 번역을 해봐야할 것 같다. 지금은 잘 모르겠다. 😂
  * [Authorization Architecture :: Spring Security - authz-voter-adaptation](https://docs.spring.io/spring-security/reference/servlet/authorization/architecture.html#authz-voter-adaptation)



### 😅 막힘...

저자님 하신대로 코드 수정을 하긴 했는데...

동작이 이상하다. 

Todo 게시글 하나를 썼을 때...



#### ACL_CLASS 테이블

| ID   | CLASS                                            |
| :--- | :----------------------------------------------- |
| 100  | org.fp024.study.spring5recipes.board.domain.Todo |



#### ACL_ENTRY 테이블

* 내용이 없음... READ, WRITE, DELETE 관련해서 ROW가 입력되어야했을 것으로 예상했는데, 입력된 행이 없었음.



#### ACL_OBJECT_IDENTITY

| ID   | OBJECT\_ID\_CLASS | OBJECT\_ID\_IDENTITY | PARENT\_OBJECT | OWNER\_SID | ENTRIES\_INHERITING |
| :--- | :---------------- | :------------------- | :------------- | :--------- | :------------------ |
| 100  | 100               | 1                    | null           | 100        | true                |



#### ACL_SID

| ID   | PRINCIPAL | SID   |
| :--- | :-------- | :---- |
| 100  | true      | admin |





### 다음 규칙은 이제 의미가 없지 않을까?

```java
      .requestMatchers(HttpMethod.DELETE, "/todos/*")
      .hasAuthority("ADMIN")
```

* 이제 ACL 퍼미션으로 권한을 관리하니 이 규칙은 의미가 없으니 지워야겠다.








## 의견

* 



---

## 기타

### ✨ 이제 부터는 lombok을 좀 엄격하게 사용해보자...

* 예상하지 못한 내용으로 바뀐 경우가 있음

* `@Getter`, `@Setter`, `@ToSring`, `@NoArgsConstructor` 정도만 사용하고 다른 것은 꼭 사용해야한다면 Delombok 해서 변환 내용 확인하고 적용하기!

  ```properties
  # default value is "log" 
  lombok.log.fieldName=LOGGER
  
  # default value is "true"
  lombok.log.fieldIsStatic=true
  
  # lombok.copyableAnnotations+={패키지 포함 전체 어노테이션 클래스 이름},...
  
  config.stopBubbling = true
  lombok.data.flagUsage=error
  lombok.value.flagUsage=error
  lombok.val.flagUsage=error
  lombok.var.flagUsage=error
  lombok.nonNull.flagUsage=error
  lombok.allArgsConstructor.flagUsage=error
  lombok.requiredArgsConstructor.flagUsage=error
  lombok.cleanup.flagUsage=error
  lombok.sneakyThrows.flagUsage=error
  lombok.synchronized.flagUsage=error
  lombok.experimental.flagUsage=error
  ```

  

### 단순하게 BOM만 사용한다면  Gradle 5.0 이상 부터 지원되는 platform 키워드를 사용하자!

```groovy
implementation platform("org.springframework:spring-framework-bom:${springVersion}")
implementation platform("org.springframework.security:spring-security-bom:${springSecurityVersion}")
```

* Spring Dependency Management 플러그인을 사용할 필요가 없음.



### hiddenHttpMethodFilter가 있으면 왠지 한글을 깨지개 만드는 것 같음..

```java
  @Override
  protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
    servletContext
        .addFilter(
            "characterEncodingFilter",
            new CharacterEncodingFilter(PROJECT_ENCODING_VALUE, true, true))
        .addMappingForUrlPatterns(null, false, "/*");

    // Form 제출을 할 때, _method Hidden 폼으로 GET, POST 이외의 요청을 하기 위해서 추가 필터 설정.
    // Ajax 요청만을 쓰면 이 필터가 필요가 없을 텐데, HTML Form은 GET/POST만 지원해서 이런 필터를 사용한 것 같다.
    // ✨ 현재 이 프로젝트가 PUT이나, DELETE 요청을 form의 POST로 보낸뒤 필터에서 _method Hidden 값을 보고 판별하므로
    //    스프링 시큐리티의 필터보다 앞에 위치해야한다.
    servletContext
        .addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter())
        .addMappingForUrlPatterns(
            null, false, "/*"); // 첫번째 인자 dispatcherTypes를 null로 두면 REQUEST로 인식 한다고 함.
  }
```

`hiddenHttpMethodFilter` 보다 `characterEncodingFilter`를 앞에둠.





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

