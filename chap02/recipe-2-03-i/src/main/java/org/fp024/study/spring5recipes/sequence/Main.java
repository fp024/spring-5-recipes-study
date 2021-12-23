package org.fp024.study.spring5recipes.sequence;

import org.fp024.study.spring5recipes.sequence.config.SequenceConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
  public static void main(String[] args) {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(SequenceConfiguration.class)) {
      SequenceGenerator generator = context.getBean(SequenceGenerator.class);

      LOGGER.info(generator.getSequence());
      LOGGER.info(generator.getSequence());
    }
  }
}
