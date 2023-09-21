package org.fp024.study.spring5recipes.court.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(
    basePackages = "org.fp024.study.spring5recipes.court",
    excludeFilters = {
      @Filter(type = FilterType.ANNOTATION, classes = Controller.class),
      @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ServletConfiguration.class),
    })
public class RootConfiguration {}
