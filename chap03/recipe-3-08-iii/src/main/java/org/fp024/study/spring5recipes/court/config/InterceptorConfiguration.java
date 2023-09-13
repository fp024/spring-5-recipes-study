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
    registry
        .addInterceptor(measurementInterceptor())
        // TODO: 리다이렉트 뷰 컨트롤러 설정을 하면 ?handlingTime=0 파라미터가 남아서 인터셉터 적용 경로에서 제거.
        //  왜 그런진 잘 모르겠다. 😅
        .excludePathPatterns("/", "/*.html")
        .order(Ordered.HIGHEST_PRECEDENCE);
    registry
        .addInterceptor(summaryReportInterceptor())
        .addPathPatterns("/reservationSummary*")
        .order(Ordered.LOWEST_PRECEDENCE);
  }

  @Bean
  MeasurementInterceptor measurementInterceptor() {
    return new MeasurementInterceptor();
  }

  @Bean
  ExtensionInterceptor summaryReportInterceptor() {
    return new ExtensionInterceptor();
  }
}
