package org.fp024.study.spring5recipes.court.config;

import java.util.List;
import java.util.Properties;
import org.fp024.study.spring5recipes.court.service.ReservationNotAvailableException;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Configuration
public class ExceptionHandlerConfiguration implements WebMvcConfigurer {

  @Override
  public void configureHandlerExceptionResolvers(
      List<HandlerExceptionResolver> exceptionResolvers) {
    exceptionResolvers.add(handlerExceptionResolver());
  }

  // @Bean // 아래 메서드를 빈으로 선언하면 Stackoverflow 오류가 나면서 상위 빈생성이 실패함
  // 메서드 자체로 빈생성에는 문제가 없는데 exceptionResolvers에 추가될 때 뭔가 문제가 생기는 것 같음. 😓
  SimpleMappingExceptionResolver handlerExceptionResolver() {
    Properties exceptionMapping = new Properties();
    exceptionMapping.setProperty(
        ReservationNotAvailableException.class.getName(), "reservationNotAvailable");

    SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
    exceptionResolver.setExceptionMappings(exceptionMapping);
    exceptionResolver.setDefaultErrorView("error");
    return exceptionResolver;
  }
}
