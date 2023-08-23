package org.fp024.study.spring5recipes.calculator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
public class Main {
  public static void main(String[] args) {

    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(CalculatorConfiguration.class)) {

      ComplexCalculator complexCalculator =
          context.getBean("complexCalculator", ComplexCalculator.class);

      complexCalculator.add(new Complex(1, 2), new Complex(2, 3));
      complexCalculator.sub(new Complex(5, 8), new Complex(2, 3));

      LOGGER.info("===================================");
      TestBean testBean = context.getBean("testBean", TestBean.class);

      testBean.test();
    }
  }

  @RequiredArgsConstructor
  @Component("testBean")
  public static class TestBean {
    private final ComplexCalculator complexCalculator;

    public void test() {
      complexCalculator.add(new Complex(1, 2), new Complex(2, 3));
      complexCalculator.sub(new Complex(5, 8), new Complex(2, 3));
    }
  }
}
