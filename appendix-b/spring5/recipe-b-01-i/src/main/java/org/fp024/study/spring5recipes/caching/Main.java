package org.fp024.study.spring5recipes.caching;

import java.math.BigDecimal;

public class Main {
  public static void main(String[] args) {
    CalculationService calculationService = new PlainCalculationService();
    for (int i = 0; i < 10; i++) {
      long start = System.currentTimeMillis();
      System.out.println(calculationService.heavyCalculation(BigDecimal.valueOf(2L), 16));
      long duration = System.currentTimeMillis() - start;
      System.out.println("Took: " + duration);
    }
  }
}
