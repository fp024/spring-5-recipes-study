package org.fp024.study.spring5recipes.court.config;

import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class AsyncConfiguration implements WebMvcConfigurer {

  @Override
  public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
    configurer.setDefaultTimeout(TimeUnit.MILLISECONDS.convert(10, TimeUnit.SECONDS));
    configurer.setTaskExecutor(mvcTaskExecutor());
  }

  @Bean
  public AsyncListenableTaskExecutor mvcTaskExecutor() {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    taskExecutor.setThreadGroupName("mvc-executor");
    return taskExecutor;
  }
}
