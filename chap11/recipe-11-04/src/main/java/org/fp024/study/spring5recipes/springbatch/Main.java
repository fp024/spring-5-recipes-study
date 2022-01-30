package org.fp024.study.spring5recipes.springbatch;

import java.util.Date;
import org.fp024.study.spring5recipes.springbatch.config.BatchConfiguration;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
  public static void main(String[] args)
      throws JobExecutionAlreadyRunningException, JobRestartException,
          JobInstanceAlreadyCompleteException, JobParametersInvalidException, InterruptedException {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(BatchConfiguration.class)) {

      JobLauncher jobLauncher = context.getBean(JobLauncher.class);
      Job job = context.getBean(Job.class);

      JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
      jobParametersBuilder.addDate("date", new Date());
      JobParameters jobParameters = jobParametersBuilder.toJobParameters();

      JobExecution jobExecution = jobLauncher.run(job, jobParameters);

      BatchStatus batchStatus = jobExecution.getStatus();
      System.out.println("Before Still running...");

      // 아래 코드가 왜 있는지 모르겠다. 여기까지라면 항상 끝난 상태였다.
      while (batchStatus.isRunning()) {
        System.out.println("Still running...");
        Thread.sleep(1000);
      }
      System.out.println("Exit status: " + jobExecution.getExitStatus().getExitCode());

      JobInstance jobInstance = jobExecution.getJobInstance();
      System.out.println("job instance Id: " + jobInstance.getId());
    }
  }
}
