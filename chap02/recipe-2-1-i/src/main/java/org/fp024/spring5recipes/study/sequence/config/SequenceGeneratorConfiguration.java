package org.fp024.spring5recipes.study.sequence.config;

import org.fp024.spring5recipes.study.sequence.SequenceGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SequenceGeneratorConfiguration {
  
  @Bean
  public SequenceGenerator sequenceGenerator() {
    SequenceGenerator seqgen = new SequenceGenerator();
    seqgen.setPrefix("30");
    seqgen.setSuffix("A");
    seqgen.setInitial(100000);
    return seqgen;
  }

}
