## 레시피 b-03-i AOP를 이용해 선언적으로 캐싱 구현하기 - Spring 6.x + Ehcache 3.x 전환

> ...

### 이번 레시피에서 확인해야할  내용

* ✅ **b-03-i**:  스프링 AOP를 이용해 선언적 캐싱 구현

* ⬜ ...

  




## 진행

* 캐시 대상 메서드에 `@Cacheable`을 붙이고 나서 캐시 읽고/쓰는 판박이 코드를 제거하게 되었다.
* 구성 클래스에도 그냥 `@EnableCaching` 만 붙여주면 되었다.



### 💡 Spring 6.x + Ehcache 3.x 전환

#### Exception in thread "main" java.lang.ClassCastException: Invalid key type, expected : java.lang.String but was : org.springframework.cache.interceptor.SimpleKey  

다 바꾸고 위의 예외가 나서 확인해보니...

Spring의 캐시 추상화가 기본적으로 org.springframework.cache.interceptor.SimpleKey를 키로 사용하기 때문에 발생하고, SimpleKey는 Spring이 캐시 메서드 호출의 인수를 표현하는 데 사용하는 클래스라고 함.

그러나 Ehcache 설정에서는 키 유형을 java.lang.String으로 지정했기 때문에, SimpleKey를 String으로 캐스팅하려고 할 때, ClassCastException이 발생함.

이 문제를 해결하려면 캐시 메서드 호출의 인수를 String으로 변환하는 키 생성기를 제공해야 했다.

```java
  @Bean
  KeyGenerator keyGenerator() {
    return new SimpleKeyGenerator() {
      @Override
      public Object generate(Object target, Method method, Object... params) {
        return method.getName()
            + Arrays.stream(params).map(Object::toString).collect(Collectors.joining("^"));
      }
    };
  }
```

* 메서드 이름 + 파라미터 이름들.. 을 더해서 String 타입 키로 만듬.

* 사용처에서는...

  ```java
    @Override
    @Cacheable(value = "calculations", keyGenerator = "keyGenerator")
    public BigDecimal heavyCalculation(BigDecimal base, int power) {
      ...
    }
  ```

  다음과 같이 키 생성기를 지정해야함.

이후 잘 동작하였다.

Spring 5 + Ehcache 2.x 환경에서는 따로 키생성기를 지정하지 않아도, 알아서 잘 되었었는데... 

하여튼 Spring 6 + Ehcache 3.x 에서는 이렇게 해야했다.




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

