package org.fp024.study.spring5recipes.court.config;

import org.fp024.study.spring5recipes.court.web.ExtensionInterceptor;
import org.fp024.study.spring5recipes.court.web.MeasurementInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // 인터셉터 순서를 상대적으로 정의하는 것 같음.
    registry.addInterceptor(measurementInterceptor()).order(Ordered.HIGHEST_PRECEDENCE);
    registry
        .addInterceptor(summaryReportInterceptor())
        .addPathPatterns("/reservationSummary*")
        .order(Ordered.LOWEST_PRECEDENCE);
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
