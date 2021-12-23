package org.fp024.study.spring5recipes.sequence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Main {
  public static void main(String[] args) {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(Main.class.getPackageName())) {
      SequenceService sequenceService = context.getBean(SequenceService.class);

      LOGGER.info(sequenceService.generate("IT"));
      LOGGER.info(sequenceService.generate("IT"));
    }
  }
}
