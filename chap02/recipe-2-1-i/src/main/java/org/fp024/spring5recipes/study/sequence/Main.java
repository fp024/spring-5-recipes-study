package org.fp024.spring5recipes.study.sequence;

import lombok.extern.slf4j.Slf4j;
import org.fp024.spring5recipes.study.sequence.config.SequenceGeneratorConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Main {
  public static void main(String[] args) {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(SequenceGeneratorConfiguration.class)) {

      SequenceGenerator generator = context.getBean("sequenceGenerator", SequenceGenerator.class);

      LOGGER.info("seq: {}", generator.getSequence());
      LOGGER.info("seq: {}", generator.getSequence());
      LOGGER.info("seq: {}", generator.getSequence());
    }
  }
}
