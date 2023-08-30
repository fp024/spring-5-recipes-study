package org.fp024.study.spring5recipes.springbatch;

import java.util.Date;
import org.fp024.study.spring5recipes.springbatch.config.BatchConfiguration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
  public static void main(String[] args) throws Exception {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(BatchConfiguration.class)) {

      JobLauncher jobLauncher = context.getBean(JobLauncher.class);
      Job job = context.getBean(Job.class);

      JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
      jobParametersBuilder.addDate("date", new Date());
      JobParameters jobParameters = jobParametersBuilder.toJobParameters();

      JobExecution jobExecution = jobLauncher.run(job, jobParameters);

      System.out.println("Before Still running...");

      while (jobExecution.getStatus().isRunning()) {
        System.out.println("Still running...");
        Thread.sleep(1000);
      }
      System.out.println("Exit status: " + jobExecution.getExitStatus().getExitCode());

      JobInstance jobInstance = jobExecution.getJobInstance();
      System.out.println("job instance Id: " + jobInstance.getId());
    }
  }
}
