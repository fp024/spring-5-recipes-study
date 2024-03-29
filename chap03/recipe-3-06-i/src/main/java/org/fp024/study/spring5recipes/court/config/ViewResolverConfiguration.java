package org.fp024.study.spring5recipes.court.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.view.XmlViewResolver;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ViewResolverConfiguration {
  private final ResourceLoader resourceLoader;

  @Bean
  public XmlViewResolver viewResolver() {
    LOGGER.info("### ViewResolverConfiguration#viewResolver() ##//#endregion");
    XmlViewResolver viewResolver = new XmlViewResolver();
    viewResolver.setLocation(resourceLoader.getResource("/WEB-INF/court-views.xml"));
    return viewResolver;
  }

  // @Bean
  // public InternalResourceViewResolver internalResourceViewResolver() {

  //   InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
  //   viewResolver.setPrefix("/WEB-INF/jsp/");
  //   viewResolver.setSuffix(".jsp");
  //   return viewResolver;
  // }
}
