## 레시피 b-01-ii Ehcache로 캐싱 구현하기 - Ehcache 3.x

> 💡 Ehcache 3.x로 다시 예제를 수정 / 테스트

### 이번 레시피에서 확인해야할  내용

* ✖️ **b-01-i**:  캐싱이 필요한 예제 준비 - Spring 5 예제와 동일해서 추가하지 않음.

* ✅ **b-01-ii**:  스프링 없이 Ehcache 적용 

* ⬜ ...

  



## 진행

스프링 없이 Ehcache만을 적용하는 예제이다.

* 디펜던시 추가

  ```groovy
  implementation (libs.ehcache) // ehcache 3.x
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

* 예제를 Ehache 3으로도 변경해봤는데, 꽤 변경할 것 있었음.😅

* XML 설정

  * 💡overflowToDisk 항목이 없어짐, 계층형 모델로 바뀜.

    > * https://groups.google.com/g/ehcache-users/c/FFHHhRW5hdg
    > * https://stackoverflow.com/questions/23307282/read-cache-data-from-file-system-or-diskpath/23358936#23358936
    >
    > Ehcache 3에서는 계층적인 저장 모델을 사용하며, 모든 데이터는 항상 가장 낮은 계층에 존재합니다.
    > 따라서 Ehcache 3에서는 디스크에 먼저 데이터를 저장하고, 자주 접근되는 데이터는 힙으로 올라옵니다.
    > `heap unit="entries"` 설정은 힙에 올라올 수 있는 객체의 최대 수를 지정합니다.
    > 이는 캐시에 저장될 수 있는 키-값 쌍의 최대 개수를 설정하는 것입니다.

  * 💡timeToIdleSeconds 항목도 제거됨.

    ```xml
    <expiry>
      <tti>600</tti>
      <!-- <ttl>3600</ttl> --> <!-- 💡tti와 ttl을 동시에 사용할 수 없음. --> 
    </expiry>
    ```

    처음부터 프로그래밍 설정 방식을 사용하거나, ExpiryPolicy 인터페이스의 구현 클래스를 만들어서 추가해줘야함.

    * 클래스 구현

      ```java
      public class CacheExpiry implements ExpiryPolicy<String, BigDecimal> {
      
        @Override
        public Duration getExpiryForCreation(String key, BigDecimal value) {
          return Duration.of(3600, ChronoUnit.SECONDS);
        }
      
        @Override
        public Duration getExpiryForAccess(String key, Supplier value) {
          return Duration.of(600, ChronoUnit.SECONDS);
        }
      
        @Override
        public Duration getExpiryForUpdate(String key, Supplier oldValue, BigDecimal newValue) {
          return Duration.of(600, ChronoUnit.SECONDS);
        }
      }
      ```

    * 사용

      ```xml
          <expiry>
            <class>org.fp024.study.spring5recipes.caching.ehcache.CacheExpiry</class>
          </expiry>
      ```

  * `<heap unit="entries">1000</heap>` 가 Deprecated 로 표시되는 현상

    heap 엘리먼트 자체가 폐기 되는 것이 아니고, B, kB 또는 MB 등의 메모리 단위가 폐기 된다고함. entries로 사용하는 것은 문제가 없음.

    

    


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

