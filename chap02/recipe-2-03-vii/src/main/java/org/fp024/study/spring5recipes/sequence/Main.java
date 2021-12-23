package org.fp024.study.spring5recipes.sequence;

import java.util.Arrays;

import org.fp024.study.spring5recipes.sequence.config.SequenceConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
  public static void main(String[] args) {
    try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
      // Context객체를 먼저 만들고 이후에 설정파일을 추가하고 리프레시 했다.
      context.register(SequenceConfiguration.class);
      context.refresh();

      LOGGER.info("beans: {}", Arrays.toString(context.getBeanDefinitionNames()));

      SequenceGenerator generator = context.getBean("sequenceGenerator", SequenceGenerator.class);

      LOGGER.info(generator.getSequence());
      LOGGER.info(generator.getSequence());
    }
  }
}
