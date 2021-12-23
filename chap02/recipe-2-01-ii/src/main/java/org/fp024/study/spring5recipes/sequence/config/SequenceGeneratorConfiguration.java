package org.fp024.study.spring5recipes.sequence.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    basePackages = "org.fp024.study",
    excludeFilters = {
      @ComponentScan.Filter(
          type = FilterType.REGEX,
          pattern = {".*sequence.*ExcludeBean"})
    })
public class SequenceGeneratorConfiguration {}
