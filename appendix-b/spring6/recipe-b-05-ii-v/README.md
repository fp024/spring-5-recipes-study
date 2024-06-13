## 레시피 b-05-ii-v 캐시 객체 추가/삭제하기 - Spring 6.x + Ehcache 3.x 전환

> ...

### 이번 레시피에서 확인해야할  내용

* ✔ **b-05-i**:  캐시 객체 추가/삭제하기 준비 프로젝트 생성

* ✅ **b-05-ii-v**:  캐시 객체 추가/삭제하기 프로젝트 완성

  

  




## 진행

* `@CachePut`과 `@CacheEvict`를 전부 활용한 프로젝트 완성

저자님이 똑같은 예제를 상황별로 좀 잘게 나눠두신 느낌이 들어서 ii ~ v의 예제는 하나로 쓰자.😅

캐시대상 조회결과가 null일 때도 무시하는 처리가 들어가 있음



* 실행

  ```
  ❯ gradle clean run
  
  > Task :run
  22:02:36.354 [main] DEBUG org.springframework.cache.ehcache.EhCacheManagerFactoryBean - Initializing EhCache CacheManager
  Get 'Unknown Customer' (result) : null
  Create new Customer (result) : Customer [id=-8397287983709663938, name=Marten Deinum]
  Get 'New Customer 1' (result) : Customer [id=-8397287983709663938, name=Marten Deinum]
  Get 'New Customer 2' (result) : Customer [id=-8397287983709663938, name=Marten Deinum]
  Get 'Updated Customer 1' (result) : Customer [id=-8397287983709663938, name=Josh Long]
  Get 'Updated Customer 2' (result) : Customer [id=-8397287983709663938, name=Josh Long]
  Get 'Deleted Customer 1' (result) : null
  Get 'Deleted Customer 2' (result) : null
  
  StopWatch 'Cache Evict and Put': running time = 2077526300 ns
  ---------------------------------------------
  ns         %     Task name
  ---------------------------------------------
  538099200  026%  Get 'Unknown Customer'  // (1)
  008224800  000%  Create New Customer     // (2)
  000477300  000%  Get 'New Customer 1'    // (3)
  000141200  000%  Get 'New Customer 2'    // (4)
  003029100  000%  Update Customer          // (5)
  506801400  024%  Get 'Updated Customer 1'  // (6)
  000184200  000%  Get 'Updated Customer 2'   // (7)
  000771000  000%  Remove Customer   // (8)
  505674500  024%  Get 'Deleted Customer 1'  // (9)
  514123600  025%  Get 'Deleted Customer 2'  // (10)
  
  22:02:38.587 [main] DEBUG org.springframework.cache.ehcache.EhCacheManagerFactoryBean - Shutting down EhCache CacheManager
  
  BUILD SUCCESSFUL in 4s
  5 actionable tasks: 5 executed
  ❯ 
  ```

완전히 보완된 실행 결과를 보면...

1. Unknown Customer 를 조회할 때.. 약 500ms가 걸림 
2. New Customer를 생성할 때, 캐시 생성도 같이하기 때문에 미세하게 소요시간이 있음. 약 8ms
3. New Customer를 조회했을 때...  약 0ms 걸림  - 캐시 내용 읽음
4. New Customer를 또 조회했을 때...  약 0ms 걸림  - 캐시 내용 읽음
5. ID는 변경 없이 Customer의 이름만 업데이트 
   * 해당 ID의 캐시가 무효화 됨
6. 새로 업데이트 된 유저 조회, 캐시가 없으므로 실제 조회해서 500ms 걸림
7. (6)에서 해당 유저에 대한 캐시가 생겼기 때문에, 조회 시간이 0ms에 근접함.
8. Remove 동작은 원본 데이터도 지워지고, 해당 id에 대한 캐시도 지워짐
9. `(9)`, `(10)` 삭제된 유저 (결과가 null) 를 조회하기 때문에 캐시를 만들지 않아 500ms가 소요됨

문제가 많이 해결된 것으로 보임. 👍👍




## 의견

* 다른 것은 다 괜찮은데... 없는 유저를 조회할 때.. 지연이 생기는 점은 좀 찝찝하긴 하다.. 😅



### 💡  Spring 6.x + Ehcache 3.x 전환

* recipe-b-06-i를 먼저 전환하면서 문제를 확인해봐서... 그쪽에 시행착오를 적어두었다.
  * 그래서 이 예제는 쉽게 바꿈.






---

## 기타

* ...

  

## 정오표

* ...
  


---

## JavaDocs

### @CacheEvict의 beforeInvocation

> 메서드가 호출되기 전에 제거가 발생해야 하는지 여부입니다.
>
> 이 속성을 true로 설정하면 메서드 결과(예: 예외 발생 여부)에 관계없이 제거가 발생합니다.
>
> 기본값은 false입니다. 이는 권고된 메소드가 성공적으로 호출된 후(즉, 호출이 예외를 발생시키지 않은 경우에만) 캐시 제거 작업이 발생함을 의미합니다. 기본값: false



---

### 문서 사용 아이콘

* ✅: Current Done
* ✔: Done
* ⬜: TODO
* ✖️: Skip
* ...

