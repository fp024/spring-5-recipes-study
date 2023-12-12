## 레시피 7-07 객체 보안 처리하기

> 이번에는 좀 복잡해보이는데... 일단 해보자.
>
> 도메인 객체마다 주체별로 접근 속성을 달리하기 
>
> * ✨ Spring Security 5.8.9 기준으로 검색이 안되서 기능이 없어진 것 인가 했는데... `spring-security-acl`를 추가해주니 Deprectaed된 클래스 없이 다 있었다.
>
>   

### 이번 레시피에서 확인해야할  내용

* ✅ ACL 서비스 설정하기
  
* ✅ 도메인 객체에 대한 ACL 관리하기
  
* ✅ 표현식을 이용해 접근 통제 결정하기

* ⬜ AffirmativeBased 사용한 부분  AuthorizationManager 사용으로 전환해보기 😅



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



#### ACL_ENTRY 테이블 (아니다 이건 문제가 아닌 것 같다. 😅)

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





## AffirmativeBased 사용한 부분  AuthorizationManager 사용으로 전환해보기 😅

이제 AuthorizationManager 로 대신 쓰라고 하는데...

```java
  // 😈 5.8.x에서는 Deprecated, 5.7.x에서는 아님.
  @Bean
  AffirmativeBased accessDecisionManager(AclEntryVoter aclEntryVoter) {
    List<AccessDecisionVoter<?>> decisionVoters =
        Arrays.asList(new WebExpressionVoter(), aclEntryVoter);
    return new AffirmativeBased(decisionVoters);
  }

```






## 의견

막히던 문제가 2가지가 있었는데 해결이 되었다.

1. 개별 메서드 테스트를 하면 다 성공인데, 통합 테스트를 하면 꼭 실패하는 메서드가 있는 현상

   > Todo 1번을 삭제하는 테스트에서, 삭제는 테스트 메서드 끝난후 롤백 되었으나, ACL 캐시는 그대로여서, 다음의  1번의 완료상태를 바꾸는 테스트가 권한없음으로 실패하는 현상이였던 것 같다.
   >
   > CacheManager를 `TransactionAwareCacheManagerProxy`로 감싸면 테스트 실패 문제가 해결되었다.

   ```java
     @Bean
     CacheManager jCacheCacheManager() throws Exception {
       var factoryBean = new JCacheManagerFactoryBean();
       factoryBean.setCacheManagerUri(new ClassPathResource("ehcache.xml").getURI());
       factoryBean.afterPropertiesSet();
   
       var cacheManager = new JCacheCacheManager(Objects.requireNonNull(factoryBean.getObject()));
       return new TransactionAwareCacheManagerProxy(cacheManager); // ✨
     }
   ```

   > **`TransactionAwareCacheManagerProxy`**
   >
   > “타겟 CacheManager에 대한 프록시로, Spring이 관리하는 트랜잭션과 동기화되는 트랜잭션 인식 캐시 객체를 노출합니다. 이 캐시 객체들은 `Cache.put` 연산을 Spring의 `org.springframework.transaction.support.TransactionSynchronizationManager`를 통해 동기화합니다. 실제 캐시 put 연산은 성공적인 트랜잭션의 after-commit 단계에서만 수행됩니다. 만약 활성 트랜잭션이 없다면, `Cache.put` 연산은 평소처럼 즉시 수행됩니다.”
   >
   > 즉, 이는 Spring 트랜잭션과 캐시 연산 사이의 동기화를 설명하고 있습니다. 트랜잭션이 활성 상태인 경우, 캐시에 데이터를 넣는 `put` 연산은 트랜잭션이 성공적으로 커밋된 후에만 수행됩니다. 반면, 트랜잭션이 활성 상태가 아닌 경우에는 `put` 연산이 즉시 수행됩니다. 이렇게 하면 트랜잭션 중에 발생할 수 있는 문제를 방지하고 데이터 일관성을 유지할 수 있습니다.

   이 문제가 가장 중료했음..

2. 다른 하나는 레거시 프로젝트에서 나타났던 문제인데.. 그곳에 작성 `->>`   [recipe-7-07-legacy](../recipe-7-07-legacy) 참고!!

   이 문제 때문에, 여기 요즘의 프로젝트 구성에는 EhCache를 3.x로 버전업하고 JCache로 사용하도록 바꿨음.





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

