## 레시피 b-01-ii Ehcache로 캐싱 구현하기

> ...

### 이번 레시피에서 확인해야할  내용

* ✔ **b-01-i**:  캐싱이 필요한 예제 준비

* ✅ **b-01-ii**:  스프링 없이 Ehcache 적용 

* ⬜ ...

  



## 진행

스프링 없이 Ehcache만을 적용하는 예제이다.

* 디펜던시 추가

  ```groovy
  implementation (libs.ehcache.v2)
  ```

  * Ehcache 2.x 버전을 사용


* 예제 프로그램 실행

  ```bash
  gradle clean run
  ```

* 실행 결과

  ```
  ❯ gradle clean run
  
  > Task :run
  65536
  Took: 511  // 💡 최초 캐시를 생성할 때만 지연이 있고 나머지는 바로 반환된 것이 보인다.
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
  
  BUILD SUCCESSFUL in 2s
  5 actionable tasks: 5 executed
  ❯          
  ```
  


## 의견

* 예제를 Ehache 3으로도 바꿔봐야겠다.




---

## 기타

### Awaitility 테스트도 그냥 넣어봤는데...

```java
@Test
void testMain() {
  await()
      .atMost(Duration.ofMillis(1500))
      .until(
          () -> {
            Main.main(null);
            return true;
          });
}
```

```
❯ gradle clean test

> Task :test

MainTests > testMain() STANDARD_OUT
    65536
    Took: 511
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
    Total Took: 894  // 💡main() 메서드의 전체 수행시간은 거의 1초가 된다.

BUILD SUCCESSFUL in 3s
7 actionable tasks: 7 executed                                 
❯
```

main() 메서드에 대해서 전체의 수행시간을 걸었을 때..  최대 1.5초 안에는 수행되게 설정을 넣었다.

캐시 인스턴스 생성하고 셧다운 할 때 시간 까지 합치면 거의 1초 되는 것 같다.

* 단순 수행시간 타임아웃만 계산한다면 JUnit 어노테이션 사용해도 될 것 같다.

  ```java
  @Test
  @Timeout(unit = TimeUnit.MILLISECONDS, value = 1500)
  void testMain_02() {
    Main.main(null);
  }
  ```

  

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

