## 레시피 b-05-i 캐시 객체 추가/삭제하기

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

