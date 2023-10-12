## 레시피 5-06 리액티브 컨트롤러로 폼 처리하기

> ...
> 

### 이번 레시피에서 확인해야할  내용

* 폼처리 부분도 리엑티브 컨트롤러 사용해보기

  

## 진행

* 이번도 마찬가지로 main() 실행은

  ```sh
  # main() 실행
  gradle clean run
  ```

* Tomcat에 올려서 실행

  ```sh
  # 소스 경로로 실행
  gradle appRun
  # War로 패키징 하여 실행
  gradle appRunWar
  ```

  


## 의견

* 여전히 일반 MVC로 처리하면 될 페이지를 부분적으로 리액티브 코드를 붙이다보니까 예제가 좀 이상해지는 것 같음 😅
  * 그래도 타임리프 적용해주신 것은 좋다. 👍




---

## 기타

#### 아직은 필요하지 않은 디펜던시
```groovy
  // JSR 303 어노테이션 검증을 하진 않으므로 이 예제에서도 추가하진 않는다.
  implementation "org.hibernate.validator:hibernate-validator:${hibernateValidatorVersion}"
  testRuntimeOnly "org.glassfish:javax.el:${javaxElVersion}"
```



### Validator의 supports가 원래 검증하려는 도메인 외에 org.springframework.web.server.session.InMemoryWebSessionStore$InMemoryWebSession를 검증하려 시도한다.

```java
@Component
public class ReservationValidator implements Validator {
  @Override
  public boolean supports(Class<?> clazz) {
    // 일반적인 MVC 라면 여기서 Reservation에 대한 지원만 확인하는데,
    // org.springframework.web.server.session.InMemoryWebSessionStore$InMemoryWebSession를 검증하려하면서
    // 오류가 난다.
    LOGGER.info("clazz: {}", clazz);
    LOGGER.info("### supports: {}", Reservation.class.isAssignableFrom(clazz));

    // 그래서 저자님은 무조건 true로 반환한 부분이 있던데..
    // return Reservation.class.isAssignableFrom(clazz);
    // 나는 다음 처럼 2가지 요건에 대해서만 추가해보자.
    return Reservation.class.isAssignableFrom(clazz) || WebSession.class.isAssignableFrom(clazz);
  }
  ...

```

* 저자님 코드를 보면 그냥 true를 반환하길레 원래 하던대로 도메인으로 바꾸고나니 오류가 나서 로그를 찍어봤음.

  * 저자님께서 `true` 반환 처리한 부분.
    * https://github.com/Apress/spring-5-recipes/blob/5fc7cc9f3e97b2486541caa1ae8bf5a95c03152b/spring-recipes-4th/ch05/recipe_5_6_i/src/main/java/com/apress/springrecipes/reactive/court/ReservationValidator.java#L16

* 처음은 Reservation에 대한 호출이 일어나는데, 두번째에 org.springframework.web.server.session.InMemoryWebSessionStore$InMemoryWebSession의 지원 여부를 확인하는 시도를하고 나중에 `DataBinder.java`아래 부분에서 실패해서 서버측 에러나는 듯.

  ```java
  	private void assertValidators(Validator... validators) {
  		Object target = getTarget();
  		for (Validator validator : validators) {
  			if (validator != null && (target != null && !validator.supports(target.getClass()))) {
  				throw new IllegalStateException("Invalid target for Validator [" + validator + "]: " + target);
  			}
  		}
  	}
  ```

* MVC환경에선는 이런 문제가 없었는데....😅 왜 그럴까?

  * 일단은 Reservation, WebSession 두가지에 대해 허용되게만 해둠.



#### 이미 있는 `코트이름` + `일자`+ `시간` 이면 에러 결과가 되야하는데.. 잘안되고 있다.

* 중단점 찍고 보았을 때..

  ```java
      if (cnt > 0) {
        return Mono.error(
            new ReservationNotAvailableException(
                reservation.getCourtName(), reservation.getDate(), reservation.getHour()));'
      } // ...
  ```

  위의 분기로  가긴 하는데... 에러로 처리가 안됨.😅

  make()메서드가 Mono.error()로 반환했지만 컨트롤러 메서드에서 반환값으로 처리하는 내용이 없어서 그런듯...

  * https://github.com/Apress/spring-5-recipes/blob/5fc7cc9f3e97b2486541caa1ae8bf5a95c03152b/spring-recipes-4th/ch05/recipe_5_6_i/src/main/java/com/apress/springrecipes/reactive/court/web/ReservationFormController.java#L43
  * Mono를 다루는 것은 나중에 이 주제에 전문적인 책을 보면서 해야할 것 같다.



#### HTML파일 input들의 required 검증은 제거함

서버측 검증기의 동작을 확인하기위해 제거해둠.



## 정오표

* ...

  


---

## JavaDocs

* ...
