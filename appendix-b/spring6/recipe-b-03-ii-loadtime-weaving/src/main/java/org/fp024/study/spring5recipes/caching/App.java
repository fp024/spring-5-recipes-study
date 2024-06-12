package org.fp024.study.spring5recipes.caching;

import java.math.BigDecimal;
import org.fp024.study.spring5recipes.caching.config.CalculationConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

  public static void main(String[] args) {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(CalculationConfiguration.class)) {

      CalculationService calculationService = context.getBean(CalculationService.class);

      for (int i = 0; i < 10; i++) {
        long start = System.currentTimeMillis();
        System.out.println(calculationService.heavyCalculation(BigDecimal.valueOf(2L), 16));
        long duration = System.currentTimeMillis() - start;
        System.out.println("Took: " + duration);
      }
    }
  }
}
