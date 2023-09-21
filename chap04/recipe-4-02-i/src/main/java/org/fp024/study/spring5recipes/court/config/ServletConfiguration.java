package org.fp024.study.spring5recipes.court.config;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.court.domain.Member;
import org.fp024.study.spring5recipes.court.domain.Members;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MarshallingView;

@Slf4j
@EnableWebMvc
@Configuration
@ComponentScan(
    basePackages = "org.fp024.study.spring5recipes.court",
    useDefaultFilters = false,
    includeFilters = {
      @Filter(type = FilterType.ANNOTATION, classes = Controller.class),
    })
public class ServletConfiguration implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("/resources/**", "/favicon.ico")
        .addResourceLocations("/resources/");

    registry.addResourceHandler("/index.html").addResourceLocations("/");

    registry
        .addResourceHandler("/webjars/**")
        .addResourceLocations("/webjars/")
        .resourceChain(false);
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("/", "/index.html");
  }

  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    registry.beanName();
  }

  @Bean
  View jsonMemberTemplate() {
    MappingJackson2JsonView view = new MappingJackson2JsonView();
    view.setPrettyPrint(true);
    return view;
  }

  @Bean
  View xmlMemberTemplate() {
    return new MarshallingView(jaxb2Marshaller());
  }

  @Bean
  public Marshaller jaxb2Marshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    marshaller.setClassesToBeBound(Members.class, Member.class);
    marshaller.setMarshallerProperties(
        Map.of(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE));
    return marshaller;
  }
}
