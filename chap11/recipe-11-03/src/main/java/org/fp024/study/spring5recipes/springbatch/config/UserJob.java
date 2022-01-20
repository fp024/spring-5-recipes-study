package org.fp024.study.spring5recipes.springbatch.config;

import org.fp024.study.spring5recipes.springbatch.UserRegistration;
import org.fp024.study.spring5recipes.springbatch.service.UserRegistrationService;
import org.fp024.study.spring5recipes.springbatch.service.UserRegistrationServiceImpl;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserJob {
  @Autowired private JobBuilderFactory jobs;

  @Autowired private StepBuilderFactory steps;

  @Bean
  public Job customMockJob() {
    return jobs.get("User Registration Mock Job").start(step1()).build();
  }

  @Bean
  public Step step1() {
    return steps
        .get("User Registration Mock Step")
        .<UserRegistration, UserRegistration>chunk(5)
        .reader(reader())
        .writer(writer())
        .build();
  }

  @Bean
  public UserRegistrationItemReader reader() {
    return new UserRegistrationItemReader(userRegistrationService());
  }

  @Bean
  public UserRegistrationItemWriter writer() {
    return new UserRegistrationItemWriter(userRegistrationService());
  }

  @Bean
  public UserRegistrationService userRegistrationService() {
    return new UserRegistrationServiceImpl();
  }
}
