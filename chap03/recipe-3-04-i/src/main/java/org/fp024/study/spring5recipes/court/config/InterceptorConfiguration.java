package org.fp024.study.spring5recipes.court.config;

import org.fp024.study.spring5recipes.court.web.ExtensionInterceptor;
import org.fp024.study.spring5recipes.court.web.MeasurementInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(measurementInterceptor());
    // 이번 레시피의 주제: 로케일을 변경할 수 있게 해주는 인터셉터 적용
    registry.addInterceptor(localeChangeInterceptor());
    registry.addInterceptor(summaryReportInterceptor()).addPathPatterns("/reservationSummary*");
  }

  @Bean
  public MeasurementInterceptor measurementInterceptor() {
    return new MeasurementInterceptor();
  }

  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    localeChangeInterceptor.setParamName("language");
    return localeChangeInterceptor;
  }

  @Bean
  public ExtensionInterceptor summaryReportInterceptor() {
    return new ExtensionInterceptor();
  }
}
