## 레시피 b-02-i 스프링 캐시 추상화를 이용해 캐시 적용하기

> ...

### 이번 레시피에서 확인해야할  내용

* ✅ **b-02-i**:  스프링 캐시 추상화 이용 - ConcurrentMapCacheManager 사용

* ⬜ ...




## 진행

이 예제 프로젝트에서는 Ehcache를 사용하지 않는다.

* 그래서 디펜던시에서도 Ehcache가 없어도 됨.

스프링에서 제공하는 ConcurrentMapCacheManager를 사용한다.




## 의견

* ...



---

## 기타

* ...

  

## 정오표

* ...
  


---

## JavaDocs

### ConcurrentMapCacheManager 

각 getCache 요청에 대해 ConcurrentMapCache 인스턴스를 지연 구축하는 CacheManager 구현입니다. 또한 런타임 시 추가 캐시 영역을 동적으로 생성하지 않고 캐시 이름 세트가 setCacheNames를 통해 사전 정의되는 'static' 모드도 지원합니다.

참고: 이는 결코 정교한 CacheManager가 아닙니다. 캐시 구성 옵션이 없습니다. 그러나 테스트나 간단한 캐싱 시나리오에는 유용할 수 있습니다. 고급 로컬 캐싱이 필요한 경우 org.springframework.cache.jcache.JCacheCacheManager, org.springframework.cache.ehcache.EhCacheCacheManager, org.springframework.cache.caffeine.CaffeineCacheManager를 고려하세요.



---

### 문서 사용 아이콘

* ✅: Current Done
* ✔: Done
* ⬜: TODO
* ✖️: Skip
* ...

