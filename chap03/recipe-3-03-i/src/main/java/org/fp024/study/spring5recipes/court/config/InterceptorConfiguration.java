package org.fp024.study.spring5recipes.court.config;

import org.fp024.study.spring5recipes.court.web.MeasurementInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(measurementInterceptor());
  }

  @Bean
  public MeasurementInterceptor measurementInterceptor() {
    return new MeasurementInterceptor();
  }
}
