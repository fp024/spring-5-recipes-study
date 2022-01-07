## 레시피 2-14 조인포인트 정보 가져오기

* 어드바이스 메서드의 인자로 org.aspectj.lang.JoinPoint 타입 인수를 선언하면 조인 포인트 정보를 얻을 수 있음.

  ```
   05:16:31.113 [Test worker] INFO  org.fp024.study.spring5recipes.calculator.CalculatorLoggingAspect - Join point kind : method-execution
      05:16:31.116 [Test worker] INFO  org.fp024.study.spring5recipes.calculator.CalculatorLoggingAspect - Signature declaring type : org.fp024.study.spring5recipes.calculator.ArithmeticCalculator
      05:16:31.117 [Test worker] INFO  org.fp024.study.spring5recipes.calculator.CalculatorLoggingAspect - Signature name : add
      05:16:31.117 [Test worker] INFO  org.fp024.study.spring5recipes.calculator.CalculatorLoggingAspect - Arguments : [1.0, 2.0]
      05:16:31.117 [Test worker] INFO  org.fp024.study.spring5recipes.calculator.CalculatorLoggingAspect - Target class : org.fp024.study.spring5recipes.calculator.ArithmeticCalculatorImpl
      05:16:31.117 [Test worker] INFO  org.fp024.study.spring5recipes.calculator.CalculatorLoggingAspect - This class : com.sun.proxy.$Proxy38
      1.0 + 2.0 = 3.0  
  ...
  ```

  주목할 만한게...  this class 값이 com.sun.proxy.$Proxy38 라는 점.. 인 것 같다.

  * Target Class: 프록시로 감싼 원본 빈

    

