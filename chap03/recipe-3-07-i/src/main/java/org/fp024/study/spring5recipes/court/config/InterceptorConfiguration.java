package org.fp024.study.spring5recipes.court.config;

import org.fp024.study.spring5recipes.court.web.ExtensionInterceptor;
import org.fp024.study.spring5recipes.court.web.MeasurementInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
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
