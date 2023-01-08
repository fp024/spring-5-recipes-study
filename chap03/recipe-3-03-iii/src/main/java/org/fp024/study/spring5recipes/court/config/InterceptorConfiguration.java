package org.fp024.study.spring5recipes.court.config;

import org.fp024.study.spring5recipes.court.web.ExtensionInterceptor;
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

    // 이번 장의 주제: 특졍 경로에 대해서만 인터셉터 적용
    registry.addInterceptor(summaryReportInterceptor()).addPathPatterns("/reservationSummary*");
  }

  @Bean
  public MeasurementInterceptor measurementInterceptor() {
    return new MeasurementInterceptor();
  }

  @Bean
  public ExtensionInterceptor summaryReportInterceptor() {
    return new ExtensionInterceptor();
  }
}
