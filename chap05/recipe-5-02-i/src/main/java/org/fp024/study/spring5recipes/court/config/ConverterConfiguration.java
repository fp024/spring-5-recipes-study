package org.fp024.study.spring5recipes.court.config;

import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.court.domain.SportTypeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@EnableWebMvc
@Configuration
public class ConverterConfiguration implements WebMvcConfigurer {

  private final SportTypeConverter sportTypeConverter;

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(sportTypeConverter);
  }
}
