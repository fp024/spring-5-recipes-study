package org.fp024.study.spring5recipes.sequence;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.GenericXmlApplicationContext;

@Slf4j
public class Main {
  public static void main(String[] args) {
    try (GenericXmlApplicationContext context =
        // AnnotationConfigApplicationContext 의 생성자 값으로 xml 컨텍스트 파일을 넣어도 효과 없다.
        new GenericXmlApplicationContext("appContext.xml")) {

      LOGGER.info("beans: {}", Arrays.toString(context.getBeanDefinitionNames()));

      SequenceGenerator generator = context.getBean("sequenceGenerator", SequenceGenerator.class);

      LOGGER.info(generator.getSequence());
      LOGGER.info(generator.getSequence());
    }
  }
}
