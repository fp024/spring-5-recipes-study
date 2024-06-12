## 레시피 b-03-ii AOP를 이용해 선언적으로 캐싱 구현하기 - Spring 6.x + Ehcache 3.x

> ...

### 이번 레시피에서 확인해야할  내용

* ✔ **b-03-i**:  스프링 AOP를 이용해 선언적 캐싱 구현

* ✔ **b-03-ii**:  AspectJ 응용하기

* ✅ **b-03-ii-loadtime-weaving**:  AspectJ 응용하기 - LoadTimeWeaving 사용

  




## 진행

`@EnableLoadTimeWeaving`를 구성 클래스에 붙이고, `-javaagent:{spring-instrument jar경로}`로 실행하면 캐싱이 안되었었음...

그래서 좀 더 확인을 해보았다..

Spring Boot 를 흉내내느라고, main이 담긴 App 클래스 자체를 설정 클래스 처럼 사용하고 있었는데..

이 구조를 버리고, 저자님과 똑같이 해보았는데...



이렇게 하니까 LoadTimeWeaving을 사용하더라도 캐시가 잘 적용이 된다. 👍👍



#### 실행 결과

```
>gradle clean run                                                                                                                                          
> Task :run
19:18:04.369 [main] DEBUG org.springframework.cache.ehcache.EhCacheManagerFactoryBean - Initializing EhCache CacheManager
65536
Took: 542
65536
Took: 0
65536
Took: 0
65536
Took: 0
65536
Took: 0
65536
Took: 0
65536
Took: 0
65536
Took: 0
65536
Took: 0
65536
Took: 0
19:18:05.851 [main] DEBUG org.springframework.cache.ehcache.EhCacheManagerFactoryBean - Shutting down EhCache CacheManager

BUILD SUCCESSFUL in 3s
5 actionable tasks: 5 executed
>       
```



### 💡 Spring 6.x + Ehcache 3.x 전환

크게 별 문제는 없는데.. 스타팅 시간이 길다... 😅 왜그럴까? 

> 스타팅 시간이 긴 이유는 javax.* 패키지 제외하고 모든 라이브러리를 위빙 대상으로 봐서 그런 것 같음.
>
> ```xml
> <aspectj>
>   <weaver options="-verbose">
>     <exclude within="package1.*"/>
>     <exclude within="package2.*"/>
>     <!-- ... -->
>   </weaver>
> </aspectj>
> ```
>
> 제외 설정 이게 잘 동작했으면 좋은데.. 잘 안되는 것 같음.. .😂 이건 깊이 볼 필요가 없는 것 같다. 위빙 자체를 쓰기가 싫음 😫




## 의견

* 그래도 왠지 LoadTimeWeaving은 가능한 사용하고 싶지가 않음.. 코드 구조 변경을 바꾸더라도 가능한 PROXY 방식으로 사용하고 싶음.





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

