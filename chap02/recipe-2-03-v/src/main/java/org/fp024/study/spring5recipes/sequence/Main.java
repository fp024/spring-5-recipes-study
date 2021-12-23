package org.fp024.study.spring5recipes.sequence;

import java.util.Arrays;

import org.springframework.context.support.GenericXmlApplicationContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
  public static void main(String[] args) {
    try (GenericXmlApplicationContext context =
        new GenericXmlApplicationContext("appContext.xml")) {

      LOGGER.info("beans: {}", Arrays.toString(context.getBeanDefinitionNames()));

      SequenceGenerator generator = context.getBean("sequenceGenerator", SequenceGenerator.class);

      LOGGER.info(generator.getSequence());
      LOGGER.info(generator.getSequence());
    }
  }
}
