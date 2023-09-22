package org.fp024.study.spring5recipes.court.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    LOGGER.info("### AutoSetting Converters: {} ###", converters);

    converters.removeIf(c -> c instanceof MappingJackson2HttpMessageConverter);
    converters.add(0, gsonHttpMessageConverter());

    LOGGER.info("### Modified Converters: {} ###", converters);
  }

  private GsonHttpMessageConverter gsonHttpMessageConverter() {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return new GsonHttpMessageConverter(gson);
  }
}
