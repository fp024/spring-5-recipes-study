package org.fp024.study.spring5recipes.springbatch;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.springbatch.config.BatchConfiguration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Main {
  public static void main(String[] args) throws Exception {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(BatchConfiguration.class)) {

      JobLauncher jobLauncher = context.getBean(JobLauncher.class);
      Job job = context.getBean(Job.class);

      JobParameters jobParameters =
          new JobParametersBuilder()
              .addDate(
                  "date", Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
              .addLong("max.count", 15L)
              .toJobParameters();

      JobExecution jobExecution = jobLauncher.run(job, jobParameters);

      System.out.println("Exit status: " + jobExecution.getExitStatus().getExitCode());
      System.out.println("job instance Id: " + jobExecution.getJobInstance().getId());
    }
    LOGGER.info("### main() 종료");
  }
}
