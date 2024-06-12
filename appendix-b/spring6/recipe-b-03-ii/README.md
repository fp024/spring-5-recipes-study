## 레시피 b-03-ii AOP를 이용해 선언적으로 캐싱 구현하기 - Spring 6.x + Ehcache 3.x 적용

> ...

### 이번 레시피에서 확인해야할  내용

* ✔ **b-03-i**:  스프링 AOP를 이용해 선언적 캐싱 구현

* ✅ **b-03-ii**:  AspectJ 응용하기

  




## 진행

* AspectJ가 들어가면 뭔가 어려워짐...😂
* `spring-instrument` 추가하고 기타 등등을 진행했는데.. 경고나 오류메시지는 전부제거했지만, 캐싱이 안됨 😅



#### 문제가... 

`@EnableLoadTimeWeaving`를 구성 클래스에 붙이고, `-javaagent:{spring-instrument jar경로}`로 실행하면 캐싱이 안됨.



#### 다른 해결방법으로는 ... 

`@EnableLoadTimeWeaving`을 사용하지 않고,  `-javaagent:{aspectweaver jar경로}`로 실행하면 캐싱이 동작함.

실행을 하면 다음과 같은 에러가 뜨는데,

```
[AppClassLoader@66d3c617] error can't determine annotations of missing type org.springframework.transaction.annotation.Transactional
when weaving type org.junit.platform.launcher.core.LauncherFactory
when weaving classes 
when weaving
 [Xlint:cantFindType]
[AppClassLoader@66d3c617] error can't determine annotations of missing type org.springframework.transaction.annotation.Transactional
when weaving type org.junit.platform.launcher.Launcher
when weaving classes
when weaving
 [Xlint:cantFindType]
...
```

없다는거 다 추가해주면 경고를 없앨 수 있기는 한데...( spring-tx,등등등... )

어플리케이션에 영향을 주진 않아서 무시한다.

resources/META-INF/aop.xml 파일에 다음 내용으로 저장.

```xml
<aspectj>
  <weaver options="-warn:none -Xlint:ignore" />
</aspectj>
```



#### 실행 결과

```
>gradle clean run

> Task :run
16:20:15.554 [main] DEBUG org.springframework.cache.ehcache.EhCacheManagerFactoryBean - Initializing EhCache CacheManager
65536
Took: 530
65536
Took: 0
65536
Took: 1
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
16:20:16.752 [main] DEBUG org.springframework.cache.ehcache.EhCacheManagerFactoryBean - Shutting down EhCache CacheManager

BUILD SUCCESSFUL in 5s
5 actionable tasks: 5 executed
>

```



### 💡 Spring 6.x + Ehcache 3.x 전환

크게 별 문제는 없는데.. 스타팅 시간이 길다... 😅 왜그럴까? 

지금 Main 클래스 구성에서 여전히 `@EnableLoadTimeWeaving`이 동작하지 않는 것은 Spring 5와 동일하다.






## 의견

* ...





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

