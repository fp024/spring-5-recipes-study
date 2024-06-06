## 레시피 16-08-i 스프링 공통 테스트 애너테이션 활용하기

> ...

### 이번 레시피에서 확인해야할  내용

* ✖️ **16-07-i ~ ii**: 중요하지 않고 중복이 될 수 있는 내용이라 넘어감.

* ✅ **16-08-i**:  `@Timed`와 `@Repeat` 애너테이션 활용하는 JUnit 예제

* ✖️ **16-08-ii** : `@Timed`와 `@Repeat` 애너테이션 활용하는 JUnit 예제 - AbstractTransactionalJUnit4SpringContextTests활용

  




## 진행

직전의 **16-07-i ~ ii** 예제는 넘어갔다. 

`AbstractTransactional[JUnit4|TestNG]SpringContextTests`가 자체적으로 JdbcTemplate을 생성하는데, 그것을 사용해 테스트 할 수 있다는 것을 보여주는 예제. 중요한 내용이 아니고 중복될 수 있는 내용이라 넘어간다.

**16-08-ii** 예제도 넘어간다. `@Timed`와 `@Repeat` 애너테이션 사용하면서 AbstractTransactionalJUnit4SpringContextTests를 확장한 클래스 테스트이다.

* ...



## 의견

* ...




---

## 기타

### JUnit 5에서 @RepeatedTest를 사용할 때 주의

```java
// @Test  // 💡 @Test와 @RepeatedTest를 함깨 사용하면 테스트가 +1번 더 실행된다. @Test는 쓰지 말아야함.
@RepeatedTest(5)
void withDraw() { ... }

```



### `@EnabledIf("#{systemProperties['spring.profiles.active'].contains('default')}")`는  프로필 값이 없을 때 NPE가 발생할 수 있다.

EL식을 평가할 때 NPE가 발생하여 테스트 자체가 실행하지 않을 수 있음. 다음과 같이 바꿈.

```java
@EnabledIf("#{systemProperties['spring.profiles.active'] != 'in-mem'}")
```



### JUnit 5에서는 @Timed가 동작하지 않는다. @Timeout을 사용해야한다.

```java
// @Timed(millis = 1000)
@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
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

