package org.fp024.study.spring5recipes.sequence.config;

import org.fp024.study.spring5recipes.sequence.SequenceGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class SequenceConfiguration {

  @Bean
  @DependsOn("datePrefixGenerator") // datePrefixGenerator 빈이 먼저 생성되야 이 빈을 생성
  public SequenceGenerator sequenceGenerator() {
    SequenceGenerator sequence = new SequenceGenerator();
    sequence.setInitial(100000);
    sequence.setSuffix("A");
    return sequence;
  }
}
