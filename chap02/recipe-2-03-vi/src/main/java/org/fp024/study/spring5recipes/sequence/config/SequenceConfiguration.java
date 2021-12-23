package org.fp024.study.spring5recipes.sequence.config;

import org.fp024.study.spring5recipes.sequence.DatePrefixGenerator;
import org.fp024.study.spring5recipes.sequence.NumberPrefixGenerator;
import org.fp024.study.spring5recipes.sequence.SequenceGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SequenceConfiguration {
  @Bean
  public DatePrefixGenerator datePrefixGenerator() {
    DatePrefixGenerator dpg = new DatePrefixGenerator();
    dpg.setPattern("yyyyMMdd");
    return dpg;
  }

  @Bean
  public NumberPrefixGenerator numberPrefixGenerator() {
    NumberPrefixGenerator npg = new NumberPrefixGenerator();
    return npg;
  }

  @Bean
  public SequenceGenerator sequenceGenerator() {
    SequenceGenerator sequence = new SequenceGenerator();
    sequence.setInitial(100000);
    sequence.setSuffix("A");
    return sequence;
  }
}
