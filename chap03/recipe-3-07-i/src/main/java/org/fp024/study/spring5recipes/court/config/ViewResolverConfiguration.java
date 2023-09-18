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
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebMvc
@Configuration
public class ViewResolverConfiguration implements WebMvcConfigurer {
  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    Map<String, MediaType> mediatypes = new HashMap<>();
    mediatypes.put("html", MediaType.TEXT_HTML);
    mediatypes.put("pdf", MediaType.APPLICATION_PDF);
    mediatypes.put("xls", MediaType.valueOf("application/vnd.ms-excel"));
    mediatypes.put("xml", MediaType.APPLICATION_XML);
    mediatypes.put("json", MediaType.APPLICATION_JSON);
    configurer.mediaTypes(mediatypes);
  }

  @Bean
  public ContentNegotiatingViewResolver contentNegotiatingViewResolver(
      ContentNegotiationManager contentNegotiationManager) {
    ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
    viewResolver.setContentNegotiationManager(contentNegotiationManager);
    viewResolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return viewResolver;
  }

  @Bean
  public InternalResourceViewResolver internalResourceViewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/jsp/");
    viewResolver.setSuffix(".jsp");
    viewResolver.setOrder(Ordered.LOWEST_PRECEDENCE);
    return viewResolver;
  }
}
