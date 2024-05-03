package org.fp024.study.spring5recipes.reactive.court;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.reactive.court.domain.SportTypeConverter;
import org.fp024.study.spring5recipes.reactive.court.service.ReservationService;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.thymeleaf.spring5.ISpringWebFluxTemplateEngine;
import org.thymeleaf.spring5.SpringWebFluxTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@EnableWebFlux
@ComponentScan
@Configuration
@RequiredArgsConstructor
public class WebFluxConfiguration implements WebFluxConfigurer {

  private final ReservationService reservationService;

  @Bean
  SpringResourceTemplateResolver thymeleafTemplateResolver() {

    final SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
    resolver.setPrefix("classpath:/templates/");
    resolver.setSuffix(".html");
    resolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
    resolver.setTemplateMode(TemplateMode.HTML);
    return resolver;
  }

  @Bean
  ISpringWebFluxTemplateEngine thymeleafTemplateEngine() {

    final SpringWebFluxTemplateEngine templateEngine = new SpringWebFluxTemplateEngine();
    templateEngine.setTemplateResolver(thymeleafTemplateResolver());
    return templateEngine;
  }

  @Bean
  ThymeleafReactiveViewResolver thymeleafReactiveViewResolver() {

    final ThymeleafReactiveViewResolver viewResolver = new ThymeleafReactiveViewResolver();
    viewResolver.setTemplateEngine(thymeleafTemplateEngine());
    viewResolver.setResponseMaxChunkSizeBytes(16384);
    return viewResolver;
  }

  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    registry.viewResolver(thymeleafReactiveViewResolver());
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // Netty 환경에서는 아래 설정을 넣어도 Webjars 가 동작하지 않는다.
    // 별도 리소스 제공 컨트롤러를 만듦 : WebjarsController
    // Tomcat 위에 War로 실행하면 될 줄 알았는데, 그래도 안됨 😅
    /*
    registry
        .addResourceHandler("/webjars/**")
        .addResourceLocations("/webjars/")
        .resourceChain(false);
    */

    registry
        .addResourceHandler("/resources/**") //
        .addResourceLocations("/statics/resources/");

    registry
        .addResourceHandler("/{filename:\\w+\\.html}", "/{filename:favicon\\.ico}") //
        .addResourceLocations("/statics/");
  }

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new SportTypeConverter(reservationService));
  }

  @Bean
  MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("messages");
    messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
    return messageSource;
  }

  @Bean
  ObjectMapper objectMapper() {
    return Jackson2ObjectMapperBuilder.json().modules(new JavaTimeModule()).build();
  }

  @Bean
  Jackson2JsonEncoder jackson2JsonEncoder() {
    return new Jackson2JsonEncoder(objectMapper());
  }

  @Bean
  Jackson2JsonDecoder jackson2JsonDecoder() {
    return new Jackson2JsonDecoder(objectMapper());
  }

  @Override
  public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
    configurer.defaultCodecs().jackson2JsonEncoder(jackson2JsonEncoder());
    configurer.defaultCodecs().jackson2JsonDecoder(jackson2JsonDecoder());
  }
}
