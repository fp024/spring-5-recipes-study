package org.fp024.study.spring5recipes.court.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;

@RequiredArgsConstructor
@Configuration
public class ViewResolverConfiguration {
  private final ResourceLoader resourceLoader;

  @Bean
  public ResourceBundleViewResolver viewResolver() {

    ResourceBundleViewResolver viewResolver = new ResourceBundleViewResolver();
    viewResolver.setBasename("court-views");
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
