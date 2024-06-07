package org.fp024.study.spring5recipes.caching;

import java.math.BigDecimal;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.EnableLoadTimeWeaving.AspectJWeaving;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

@Configuration
@EnableLoadTimeWeaving
@EnableCaching(mode = AdviceMode.ASPECTJ)
@ComponentScan
public class App {

  private final CalculationService calculationService;

  public App(CalculationService calculationService) {
    this.calculationService = calculationService;
  }

  public static void main(String[] args) {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(App.class)) {
      context.getBean(App.class).run();
    }
  }

  public void run() {
    for (int i = 0; i < 10; i++) {
      long start = System.currentTimeMillis();
      System.out.println(calculationService.heavyCalculation(BigDecimal.valueOf(2L), 16));
      long duration = System.currentTimeMillis() - start;
      System.out.println("Took: " + duration);
    }
  }
}
