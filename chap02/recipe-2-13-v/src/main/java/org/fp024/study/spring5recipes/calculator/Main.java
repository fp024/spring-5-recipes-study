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
      arithmeticCalculator.div(4, 0); // 0으로 나눠서 일부러 예외를 발생시킨다.

      UnitCalculator unitCalculator = context.getBean("unitCalculator", UnitCalculator.class);
      unitCalculator.kilogramToPound(10);
      unitCalculator.kilometerToMile(5);
    } catch (IllegalArgumentException e) {
      if (!"Division by zero".equals(e.getMessage())) {
        throw e;
      }
    }
  }
}
