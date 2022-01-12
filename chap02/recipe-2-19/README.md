## 레시피 2-19 AOP를 이용해 POJO에 상태 추가하기

* CalculatorIntroduction 클래스

  * 멤버 변수가 있는 Counter 구현 클래스를 *CalculatorImpl 클래스에 추가했다.

  * 상태 변경(Counter 구현체의  count 멤버 변수의 변경)을 위해서는 @After메서드 어드바이스를 정의 해야함

    ```java
    @After(
          "execution(* org.fp024.study.spring5recipes.calculator.*Calculator.*(..))"
              + " && this(counter)")
      public void increaseCount(Counter counter) {
        counter.increase();
      }
    ```

    Counter 인터페이스를 구현한 객체는 프록시가 유일하므로 target이 아닌 this객체를 가져와 사용해야함.

* LoggingRequired 어노테이션은 실제로 사용하지 않기 때문에 추가하지 않았다.
