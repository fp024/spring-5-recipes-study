## 레시피 7-07 객체 보안 처리하기 - 이전버전의 Spring Security

> 일단은 기본은 Spring Security `5.7.11`  기준으로 써보자.. 버전을 너무 낮춰도 문제가 있음. 😅

### 이번 레시피에서 확인해야할  내용

* ✅ ACL 서비스 설정하기
  
* ✅ 도메인 객체에 대한 ACL 관리하기
  
* ✅ 표현식을 이용해 접근 통제 결정하기



## 진행

이번에는 꽤 의도대로 진행이 되었다.

Spring Security는 `5.0.19.RELEASE`, `5.7.11` 에서 확인했는데.. ACL이 동작함을 확인함.




## 의견

* ...



---

## 기타

#### ✨ 확인해야할 부분

1. `ResourceDatabasePopulator`를 사용할 때..  `TodoInitializer`와 같이 `@PostConstruct` 붙은 메서드에 데이터를 넣어주는 로직이 들어간 것과 같이 사용한다면 `ResourceDatabasePopulator`이게 나중에 수행되는 것 같음.

   > 일단은 DataSource 생성하면 바로 ResourceDatabasePopulator를 바로 execute하게 바꿔둠.
   >
   > 나중에 매끄러운 방법을 알게 되면 바꾸자!

2. 테스트가 이상하다. (해결했음.. ✔ )

   > 메서드를 개별 실행할 때는 잘 동작하도록 몇몇 코드 수정을 하긴 했는데...
   >
   > 전체를 한번에 돌리면 몇몇 테스트 메서드가 랜덤으로 실패함.

   💡 해결했음.. CacheManager를 `TransactionAwareCacheManagerProxy` 로 감싸서 트랜젝션이 커밋 완료됬을 때만 Cache put 되도록 하면 됨.
   

3. 테스트 할 때.. `@DirtyContext`를 붙여도 EhCache 관련 예외가 발생

   > 이것도 지금은 잘 모르겠음.. 
   
   💡 이것도 해결을 하긴 했는데.. `@DirtyContext` 는 제거하고 캐시메니저 만들 때 `EhCacheManagerUtils.buildCacheManager()`로 만들어서 전달해주면 에러가 발생하지 않음.
   
   ```java
   // 기존
   @Bean
   CacheManager ehCacheManager() {
     return new EhCacheCacheManager();
   }
   ```
   
   
   
   ```java
     // ✨✨✨ 수정
     @Bean
     CacheManager ehCacheManager() {
       var cacheManager =
           new EhCacheCacheManager(EhCacheManagerUtils.buildCacheManager());
       return new TransactionAwareCacheManagerProxy(cacheManager);
     }
   ```
   
   



## 정오표

* https://github.com/Apress/spring-5-recipes/blob/5fc7cc9f3e97b2486541caa1ae8bf5a95c03152b/spring-recipes-4th/ch07/recipe_7_7_i/src/main/java/com/apress/springrecipes/board/TodoServiceImpl.java#L75-L79

  * 이건 나중 규칙이 바뀐지는 모르겠으나.. 지금 내 환경에서는 오류가 발생한다.

  * `@PostFilter`를 쓸 때는 filterObject가 컬랙션 타입이여야한다고 에러남 😂 나는 아래 처럼 `@PostAuthorize` 사용하도록 바꾸었음.

    ```java
      @Override
      @PostAuthorize("hasPermission(returnObject, 'read')") // ✨
      public Todo findById(long id) {
        return todoRepository.findOne(id);
      }
    ```
    






---

## JavaDocs

* ...



---

### 문서 사용 아이콘

* ✅
* ✔
* ⬜
* ...

