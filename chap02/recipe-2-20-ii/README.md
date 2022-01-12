## 레시피 2-20-i i AspectJ 애스펙트를 로드 타임 위빙하기

* 복소수 생성 부분을 Aspect를 사용해서 캐시화 하였는데, AspectJ Weaver로 로드타임에 위빙하려면, 아래와 같이 실행시점에 javaagent를 설정해줘야한다.

  ```
  java -javaagent:lib/aspectjweaver-x.x.x.jar -jar recipe-2-20-ii.jar
  ```

* 그런데, Gradle에서 이것을 해주는 플러그인이 있어서, 해당 플러그인 적용후 캐시 동작을 확인할 수 있었다.

  ```groovy
  id "io.freefair.aspectj.post-compile-weaving" version "${postCompileWeavingVersion}"
  ```

* 동작 확인

  ```
  > Task :chap02:recipe-2-20-ii:run
  Cache MISS for (1,2)
  Cache MISS for (2,3)
  Cache MISS for (3,5)
  (1 + 2i) + (2 + 3i) = (3 + 5i)
  Cache MISS for (5,8)
  Cache HIT for (2,3)
  Cache HIT for (3,5)
  (5 + 8i) - (2 + 3i) = (3 + 5i)
  
  BUILD SUCCESSFUL in 1s
  ```

  

### 참고

* https://plugins.gradle.org/plugin/io.freefair.aspectj.post-compile-weaving
