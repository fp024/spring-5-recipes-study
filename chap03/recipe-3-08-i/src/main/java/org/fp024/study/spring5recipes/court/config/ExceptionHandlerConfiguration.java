package org.fp024.study.spring5recipes.court.config;

import java.util.List;
import java.util.Properties;
import org.fp024.study.spring5recipes.court.service.ReservationNotAvailableException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@EnableWebMvc
@Configuration
public class ExceptionHandlerConfiguration implements WebMvcConfigurer {

  @Override
  public void configureHandlerExceptionResolvers(
      List<HandlerExceptionResolver> exceptionResolvers) {
    exceptionResolvers.add(handlerExceptionResolver());
  }

  @Bean
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
