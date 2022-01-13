package org.fp024.study.spring5recipes.calculator;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
  public static void main(String[] args) {

    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(CalculatorConfiguration.class)) {

      ComplexCalculator complexCalculator =
          context.getBean("complexCalculator", ComplexCalculator.class);

      complexCalculator.add(new Complex(1, 2), new Complex(2, 3));
      complexCalculator.sub(new Complex(5, 8), new Complex(2, 3));
    }
  }
}
