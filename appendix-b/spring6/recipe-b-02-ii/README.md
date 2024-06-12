## 레시피 b-02-ii 스프링 캐시 추상화를 이용해 캐시 적용하기 - Spring 6.x + Ehcache 3.x 전환

> ...

### 이번 레시피에서 확인해야할  내용

* ✔ **b-02-i**:  스프링 캐시 추상화 이용 - ConcurrentMapCacheManager 사용

* ✅ **b-02-ii**:  Ehcache를 스프링 캐싱 추상화에 사용하기

  




## 진행

* 다시 Ehcache를 사용한다.
* 이미 다른 코드들을 스프링 캐시 추상화에 맞게 변경하였기 때문에, 설정 클래스만 바꿔주면 되었다.



### 💡 Spring 6.x + Ehcache 3.x 전환

* 이미 recipe-b-01-iv를 전환할 때.. JCache 전환을 해봐서, 예제 코드가 똑같게 되어버렸다. 

* 사소한 차이가 있다면...

  ```java
      ValueWrapper result = cache.get(key);
      if (result != null) {
        return (BigDecimal) result.get();
      }   
  ```

  이 부분을 이렇게 할 필요가 없고,

  ```java
      BigDecimal result = cache.get(key, BigDecimal.class);
      if (result != null) {
        return result;
      }
  ```

  캐시를 가져올 때.. 타입을 같이 지정해주면 캐스팅을 할 필요가 없다.






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

