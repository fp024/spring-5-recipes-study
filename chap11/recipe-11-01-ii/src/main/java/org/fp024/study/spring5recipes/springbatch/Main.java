package org.fp024.study.spring5recipes.springbatch;

import org.fp024.study.spring5recipes.springbatch.config.BatchConfiguration;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
  public static void main(String[] args) {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(BatchConfiguration.class)) {

      JobRegistry jobRegistry = context.getBean(JobRegistry.class);
      JobLauncher jobLauncher = context.getBean(JobLauncher.class);
      JobRepository jobRepository = context.getBean(JobRepository.class);

      System.out.println("JobRegistry: " + jobRegistry);
      System.out.println("JobLauncher: " + jobLauncher);
      System.out.println("JobRepository: " + jobRepository);
    }
  }
}
