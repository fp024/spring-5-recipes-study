package org.fp024.study.spring5recipes.hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Main {
  public static void main(String[] args) {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(BeansConfig.class)) {
      HelloWorld helloWorld = context.getBean(HelloWorld.class);
      helloWorld.hello();
      LOGGER.info("holidays: {}", helloWorld.getHolidays());
    }
  }
}
