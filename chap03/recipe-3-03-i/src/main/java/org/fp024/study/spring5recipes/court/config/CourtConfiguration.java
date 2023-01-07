package org.fp024.study.spring5recipes.court.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc // 이 어노테이션을 붙여야 인터셉터가 정상 등록됨
@ComponentScan("org.fp024.study.spring5recipes.court")
public class CourtConfiguration {

  @Bean
  public InternalResourceViewResolver internalResourceViewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/jsp/");
    viewResolver.setSuffix(".jsp");
    return viewResolver;
  }
}
