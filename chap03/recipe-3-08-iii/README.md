## 레시피 3-08-iii 뷰에 예외 매핑하기 - `@ExceptionHandler` 어노테이션 설정

> 3-08-i 에서는 [ExceptionHandlerConfiguration.java](https://github.com/fp024/spring-5-recipes-study/blob/master/chap03/recipe-3-08-i/src/main/java/org/fp024/study/spring5recipes/court/config/ExceptionHandlerConfiguration.java) 에 예외 핸들러를 설정했지만, 이번에는 `@ExceptionHandler`와 `@ControllerAdvice`를 사용해서 설정한다.

### 이번 레시피에서 확인해야할  내용

* ...

  

## 진행

>  이번 예제에서는 별로 할 것은 없고.. `ExceptionHandlingAdvice` 만 정의해주면 되는데...
>
> ```java
> @ControllerAdvice
> public class ExceptionHandlingAdvice {
> 
>   @ExceptionHandler(ReservationNotAvailableException.class)
>   public String handle(ReservationNotAvailableException e, Model model) {
>     model.addAttribute("exception", e); // 이 부분 추가되야함.
>     return "reservationNotAvailable";
>   }
> 
>   @ExceptionHandler
>   public String handleDefault(Exception e) {
>     return "error";
>   }
> }
> ```
>
> `SimpleMappingExceptionResolver`를 설정 클래스에 설정해서 사용할 경우라면, `DEFAULT_EXCEPTION_ATTRIBUTE`를 설정해주기 때문에 할필요가 없지만.. 위처럼 `@ExceptionHandler`로 설정한 경우라면 예외 인스턴스를 model에 설정해야한다. 



## 의견

* ...

