## 레시피 b-05-i 캐시 객체 추가/삭제하기 - Spring 6.x + Ehcache 6.x 전환

> ...

### 이번 레시피에서 확인해야할  내용

* ✅ **b-05-i**:  캐시 객체 추가/삭제하기 준비 프로젝트 생성

* ⬜ ...

  

  




## 진행

* `@CachePut`과 `@CacheEvict`를 활용하지 않는준비 프로젝트만 만듬.

* 실행

  ```
  ❯ gradle clean run
  
  > Task :run
  21:25:28.099 [main] DEBUG org.springframework.cache.ehcache.EhCacheManagerFactoryBean - Initializing EhCache CacheManager
  Get 'Unknown Customer' (result) : null
  Create new Customer (result) : Customer [id=-8737304094364382573, name=Marten Deinum]
  Get 'New Customer 1' (result) : Customer [id=-8737304094364382573, name=Marten Deinum]
  Get 'New Customer 2' (result) : Customer [id=-8737304094364382573, name=Marten Deinum]
  Get 'Updated Customer 1' (result) : Customer [id=-8737304094364382573, name=Josh Long]
  Get 'Updated Customer 2' (result) : Customer [id=-8737304094364382573, name=Josh Long]
  Get 'Deleted Customer 1' (result) : Customer [id=-8737304094364382573, name=Josh Long]
  Get 'Deleted Customer 2' (result) : Customer [id=-8737304094364382573, name=Josh Long]
  
  StopWatch 'Cache Evict and Put': running time = 1023532600 ns
  ---------------------------------------------
  ns         %     Task name
  ---------------------------------------------
  506423600  049%  Get 'Unknown Customer'    // (1)
  003673400  000%  Create New Customer       // (2)
  512100600  050%  Get 'New Customer 1'      // (2)
  000563600  000%  Get 'New Customer 2'      // (3)
  000145400  000%  Update Customer           // (4)
  000138800  000%  Get 'Updated Customer 1'  // (5) 
  000130200  000%  Get 'Updated Customer 2'  // (6)
  000116900  000%  Remove Customer           // (7)
  000126600  000%  Get 'Deleted Customer 1'  // (8) 
  000113500  000%  Get 'Deleted Customer 2'  // (9)
  
  21:25:29.267 [main] DEBUG org.springframework.cache.ehcache.EhCacheManagerFactoryBean - Shutting down EhCache CacheManager
  
  BUILD SUCCESSFUL in 3s
  5 actionable tasks: 5 executed
  ❯
  
  ```

지금 유저를 조회할 때만 캐시가 설정되어있는데, 

1. Unknown Customer 를 조회할 때.. 약 500ms가 걸림 
2. New Customer를 생성하고 조회를 했을 때.. 약 500ms 걸림
3. New Customer를 또 조회했을 때...  약 0ms 걸림
4. ID는 변경 없이 Customer의 이름만 업데이트 
   * 원본을 업데이트한 것이아니고, 캐시된 내용을 업데이트 했다.
5. `(5)`, `(6)` 원본 데이터를 가지고 오는 것이 아니고 캐시된 데이터를 가져오므로 sleep()에 걸리지 않았다.
6.  ""
7. Remove 동작은 원본 데이터가 지워짐, 그러나 캐시는 여전히 남아있음.
8. `(8)`, `(9)` 캐시는 여전히 그대로 있어서, 조회가 그냥 됨.. 😅



현상태에서는 문제가 있음을 알 수 있다. 😅




## 의견

* `org.springframework.util.StopWatch`가 상당히 쓸만해보임

  * 수행시간 검사하기 좋게 되어있음.



### 💡 Spring 6.x + Ehcache 6.x 전환

전에 겪지 못했던 문제를 겪게 됨...

캐시 대상 메서드가 null을 반환할 경우에 대한 문제인데..

```
17:11:27.055 [main] INFO  org.ehcache.core.EhcacheManager - Cache 'customers' removed from EhcacheManager.
Exception in thread "main" java.lang.ClassCastException: Invalid value type, expected : org.fp024.study.spring5recipes.caching.domain.Customer but was : org.springframework.cache.support.NullValue
        at org.ehcache.impl.store.BaseStore.checkValue(BaseStore.java:73)
        at org.ehcache.impl.internal.store.heap.OnHeapStore.put(OnHeapStore.java:323)
        at org.ehcache.core.Ehcache.doPut(Ehcache.java:93)
        at org.ehcache.core.EhcacheBase.put(EhcacheBase.java:187)
        at org.ehcache.jsr107.Eh107Cache.put(Eh107Cache.java:175)
        at org.springframework.cache.jcache.JCacheCache.put(JCacheCache.java:101)
```

캐시를 저장할 때, ehcache 구현에서 저장 된 값에 대한 타입 체크를 하는데, Spring에서는 null일 경우는 `org.springframework.cache.support.NullValue`를 캐시에 넣기 때문에, 캐스팅 예외가 발생함.

결국 value-type을 별도 지정하지 말고 Object로 사용하는 것이 나을 것 같다.

Object로 하더라도 스프링 캐시 추상화 상태에서는 내가 코드상에서 직접 캐스팅할일은 없음.

```xml
<value-type>java.lang.Object</value-type>
```

이게 기본 값이라 생략해도 됨.






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

* ✅: Current Done
* ✔: Done
* ⬜: TODO
* ✖️: Skip
* ...

