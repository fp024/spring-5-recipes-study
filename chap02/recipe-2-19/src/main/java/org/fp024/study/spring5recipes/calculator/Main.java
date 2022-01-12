package org.fp024.study.spring5recipes.calculator;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
  public static void main(String[] args) {

    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(CalculatorConfiguration.class)) {

      ArithmeticCalculator arithmeticCalculator =
          context.getBean("arithmeticCalculator", ArithmeticCalculator.class);
      arithmeticCalculator.add(1, 2);
      arithmeticCalculator.sub(4, 3);
      arithmeticCalculator.mul(2, 3);
      arithmeticCalculator.div(4, 2);

      UnitCalculator unitCalculator = context.getBean("unitCalculator", UnitCalculator.class);
      unitCalculator.kilogramToPound(10);
      unitCalculator.kilometerToMile(5);

      MaxCalculator maxCalculator = (MaxCalculator) arithmeticCalculator;
      maxCalculator.max(1, 2);

      MinCalculator minCalculator = (MinCalculator) arithmeticCalculator;
      minCalculator.min(1, 2);

      // 2-19 예제의 주제
      Counter arithmeticCounter = (Counter) arithmeticCalculator;
      System.out.println(arithmeticCounter.getCount());

      Counter unitCounter = (Counter) unitCalculator;
      System.out.println(unitCounter.getCount());
    }
  }
}
