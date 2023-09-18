package org.fp024.study.spring5recipes.court.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebMvc
@Configuration
public class ViewResolverConfiguration implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/index.html").addResourceLocations("/resources/");
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("/", "/index.html");
  }

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    Map<String, MediaType> mediaTypes = new HashMap<>();
    mediaTypes.put("html", MediaType.TEXT_HTML);
    mediaTypes.put("pdf", MediaType.APPLICATION_PDF);
    mediaTypes.put("xls", MediaType.valueOf("application/vnd.ms-excel"));
    mediaTypes.put("xml", MediaType.APPLICATION_XML);
    mediaTypes.put("json", MediaType.APPLICATION_JSON);
    configurer.mediaTypes(mediaTypes);
  }

  @Bean
  ContentNegotiatingViewResolver contentNegotiatingViewResolver(
      ContentNegotiationManager contentNegotiationManager) {
    ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
    viewResolver.setContentNegotiationManager(contentNegotiationManager);
    viewResolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return viewResolver;
  }

  @Bean
  InternalResourceViewResolver internalResourceViewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/jsp/");
    viewResolver.setSuffix(".jsp");
    viewResolver.setOrder(Ordered.LOWEST_PRECEDENCE);
    return viewResolver;
  }
}
