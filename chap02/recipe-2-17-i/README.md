## 레시피 2-17-i AspectJ 포인트컷 표현식 작성하기

* 예제가 XML 설정으로 되어있는데, 어노테이션 방식으로 쓰자!
  * XML에 아래 내용 추가
  `<aop:aspectj-autoproxy />`

  * 설정 클래스에 아래 어노테이션 붙임
  `@EnableAspectJAutoProxy`



### 이번 예제가 좀 이상하다..

기왕 LoggingRequred 어노테이션을 사용자정의해서 만들었으니, 어노테이션만 붙은 걸로 해보려하는데...

아래와 같았다.

* CalculatorPointcuts 클래스

  ```java
  @Aspect
  public class CalculatorPointcuts {
  
    // @Pointcut("within(@LoggingRequired *)")   // 대상 클래스에 붙였을 때 적용됨
    @Pointcut("@annotation(LoggingRequired)")    // 대상 메서드에 어노테이션을 붙였을 때 적용됨
    public void loggingOperation() {}
  }
  ```

  

2-17-i 예제를 소스를 보면 LoggingRequred  어노테이션 정의만하고 실제 활용하지 않고 있다.

그리고 @PointCut에서 어노테이션 클래스를 불러올 때, @를 annotation앞에 붙여야했다.

그래서 활용하게 바꾸고..  ArithmeticCalculatorImpl 클래스의 add() 메서드에 @LoggingRequired를 붙여서 동작확인해보았다.