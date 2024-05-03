package org.fp024.study.spring5recipes.board.config;

import static org.fp024.study.spring5recipes.board.common.Constants.PROJECT_ENCODING_VALUE;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
@EnableWebMvc
@ComponentScan(
    value = "org.fp024.study.spring5recipes.board",
    useDefaultFilters = false,
    includeFilters = {
      @Filter(classes = Controller.class),
    })
public class TodoWebConfig implements WebMvcConfigurer {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("redirect:/index");
  }

  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    registry.viewResolver(thymeleafViewResolver());
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("/*.html", "/favicon.ico") //
        .addResourceLocations("classpath:/statics/");

    registry
        .addResourceHandler("/webjars/**")
        .addResourceLocations("/webjars/")
        .resourceChain(false);
  }

  @Bean
  SpringResourceTemplateResolver thymeleafTemplateResolver() {

    final SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
    resolver.setPrefix("classpath:/templates/");
    resolver.setSuffix(".html");
    resolver.setCharacterEncoding(PROJECT_ENCODING_VALUE);
    resolver.setTemplateMode(TemplateMode.HTML);
    return resolver;
  }

  @Bean
  SpringTemplateEngine thymeleafTemplateEngine() {

    final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.addDialect(new SpringSecurityDialect());
    templateEngine.setTemplateResolver(thymeleafTemplateResolver());
    return templateEngine;
  }

  @Bean
  ThymeleafViewResolver thymeleafViewResolver() {
    final ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    viewResolver.setTemplateEngine(thymeleafTemplateEngine());
    // 아래 인코딩 설정이 필수였다. 이것을 설정하지 않으면 한글 처리 자체가 안됨.
    viewResolver.setCharacterEncoding(PROJECT_ENCODING_VALUE);
    return viewResolver;
  }
}
