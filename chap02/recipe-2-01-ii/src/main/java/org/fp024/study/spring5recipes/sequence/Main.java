package org.fp024.study.spring5recipes.sequence;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.sequence.config.SequenceGeneratorConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Main {
  public static void main(String[] args) {

    LOGGER.info("Main class package name: {}", Main.class.getPackage().getName());

    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(SequenceGeneratorConfiguration.class)) {

      List<String> beanNames = Arrays.asList(context.getBeanDefinitionNames());
      LOGGER.info("beanNames : {}", beanNames);

      if (!beanNames.contains("sequenceDao")) {
        throw new IllegalStateException("sequenceDao 빈은 만들어져야함.");
      }

      if (beanNames.contains("excludeBean")) {
        throw new IllegalStateException("excludeBean 빈은 만들어지지 말아야함.");
      }

      SequenceDao sequenceDao = context.getBean("sequenceDao", SequenceDao.class);

      LOGGER.info("sequence: {}", sequenceDao.getSequence("IT"));
      LOGGER.info("next value: {}", sequenceDao.getNextValue("IT"));
    }
  }
}
