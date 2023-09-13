## 레시피 3-08-i 뷰에 예외 매핑하기 ~ 3-08-ii 포함

> 예제의 메인 HTML 페이지를 만들고 링크를 걸자.

### 이번 레시피에서 확인해야할  내용

* ...

  

## 진행

3-8-i 예제와  3-8-ii 예제가 단순히 예외 페이지를 사용자 설정을 했는지의 여부밖에 차이가 안나서, 여기다 예외 설정을 넣고, 3-8-ii는 안만들어도 될 것 같다.





## 의견

* 역자님 코드에서 SportTypeConverter가 컴포넌트로는 등록이 되어있는데... addFormatters에 추가가 되지 않아서 등록이 안되는 상태였다. 

  ```java
  @RequiredArgsConstructor
  @Configuration
  public class ConverterConfiguration implements WebMvcConfigurer {
  
    private final SportTypeConverter sportTypeConverter;
  
    @Override
    public void addFormatters(FormatterRegistry registry) {
      registry.addConverter(sportTypeConverter);
    }
  }
  ```

  설정을 추가했다.



## 기타

#### 1. `/` 경로로 접근하면 welcome으로 이동하게 리다이렉트 뷰를 설정했는데, URL파라미터에 `handlingTime=0` 가 붙는다?

* 이유는 잘 모르겠음. 
* `/`로 접근시 인덱스 HTML 페이지로 접근하게 바꾸고 measurementInterceptor에서는 이 경로는 처리되지 않게 설정함.



#### 2. ResourceBundleViewResolver 는 Deprecated 됨 (Spring 5.3부터)

* 저자님 코드에서 cort-views.properties 에 welcomeRedirect 뷰 이름으로 접근시 welcome으로 리다이렉트 하는 설정이 있고, ResourceBundleViewResolver  설정이 구성되어있다.
* ResourceBundleViewResolver 의 Deprecared 관련 빙 답변.

> ResourceBundleViewResolver는 Spring 5.3 버전부터 deprecated 되었습니다. 이는 Spring의 일반적인 뷰 리졸버 변형 및/또는 사용자 정의 리졸버 구현을 선호하기 때문입니다.
>
> 따라서 ResourceBundleViewResolver 대신 다른 뷰 리졸버를 사용하는 것이 좋습니다. 예를 들어, InternalResourceViewResolver나 BeanNameViewResolver 등을 사용할 수 있습니다. 이러한 대체 방법은 애플리케이션의 요구 사항과 구조에 따라 달라질 수 있습니다.
>
> 기존의 레거시 애플리케이션을 유지하면서 새로운 기능을 추가하거나 기존 기능을 개선하는 것은 어려울 수 있습니다. 그러나 이러한 변경은 애플리케이션을 최신 상태로 유지하고, 최신 기술 트렌드를 따르는 데 중요합니다.

* 이 부분은 따라하지 말자.



### 3.  예약 할 때.. ` @SessionAttributes`를 왜 사용했을까... 물어봄.

* https://github.com/Apress/spring-5-recipes/blob/master/spring-recipes-4th/ch03/recipe_3_8_i/src/main/java/com/apress/springrecipes/court/web/ReservationFormController.java

> `@SessionAttributes("reservation")` 어노테이션은 Spring MVC에서 세션을 관리하는 데 도움이 됩니다. 이 어노테이션은 특정 컨트롤러에서 사용되는 세션 속성을 선레024언합니다.
>
> "reservation"이라는 이름의 모델 속성이 세션에 자동으로 저장되도록 합니다. 이 속성은 여러 요청에 걸쳐 사용될 수 있으며, 이는 여러 페이지 또는 요청에서 동일한 속성을 참조해야 하는 경우 유용합니다.
>
> 예를 들어, 사용자가 예약 프로세스를 거치는 동안 여러 페이지에서 `reservation` 객체를 사용해야 하는 경우, `@SessionAttributes("reservation")`을 사용하여 ‘reservation’ 객체를 세션에 저장하고 필요할 때마다 쉽게 검색할 수 있습니다.
>
> 그러나 주의해야 할 점은 `@SessionAttributes`가 세션에 속성을 영구적으로 저장하지 않는다는 것입니다. 이 어노테이션은 단지 웹 세션에 임시로 속성을 저장하는 데 사용됩니다. 따라서 로그인 정보와 같은 영구적인 세션 데이터를 저장하는 데는 적합하지 않습니다.

💡 난 무조건 세션을 쓰지 않는게 맞다고 생각하긴 했는데.. 뭔가 써야할 필요가 있을 때가 있을 것 같다.



### 4. 상당히 짜증나는 문제. 😈 => `해결됨`  🎉

> 5.3.29의 문제인지는 모르겠으나.. @Bean으로 등록하면 StackOverflow오류가 발생하면서 빈생성이 실패함.

```java
@Configuration
public class ExceptionHandlerConfiguration implements WebMvcConfigurer {

  @Override
  public void configureHandlerExceptionResolvers(
      List<HandlerExceptionResolver> exceptionResolvers) {
    exceptionResolvers.add(handlerExceptionResolver());
  }

  // @Bean // Bean으로 설정하지 않으면 잘동작함.
  SimpleMappingExceptionResolver handlerExceptionResolver() {
    Properties exceptionMapping = new Properties();
    exceptionMapping.setProperty(
        ReservationNotAvailableException.class.getName(), "reservationNotAvailable");

    SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
    exceptionResolver.setExceptionMappings(exceptionMapping);
    exceptionResolver.setDefaultErrorView("error");
    return exceptionResolver;
  }
}
```

이래서 부트를 써야하는게 맞는것 같다... ㅠㅠ 그냥 쓰면 뭔가 알 수 없는 문제가 너무 많이생김..😂

정확히 책의 내용을 따랐는데도, 뭔가 알 수 없는 문제로 너무 막히면 오류가 어떻게 난다 정도만 적고 넘어가야겠다.

> 해결되었다.. `@EnableWebMvc`를 붙여주면 `@Bean`으로 선언해도 문제없이 잘 동작하였음. 😅
>
> * 💡 `WebMvcConfigurer` 인터페이스를 구현한 설정 클래스에는 `@EnableWebMvc`를 꼭 붙여줘야하나봄.
