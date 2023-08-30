package org.fp024.study.spring5recipes.springbatch.config;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class JobScheduler {

  private final AtomicInteger count = new AtomicInteger(0);

  private final JobLauncher jobLauncher;

  private final Job job;

  private final ThreadPoolTaskScheduler scheduler;

  public void runRegistrationJob(LocalDateTime dateTime) throws Exception {
    LOGGER.info("Starting job at {}", dateTime.toString());

    JobParameters jobParameters =
        new JobParametersBuilder()
            .addDate(
                "date", Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
            .addString("input.file", "registrations")
            .toJobParameters();

    JobExecution jobExecution = jobLauncher.run(job, jobParameters);

    LOGGER.info("jobExecution finished, exit: {}", jobExecution.getExitStatus());
  }

  @Scheduled(fixedDelay = 1000 * 3)
  public void runRegistrationJobOnAsSchedule() throws Exception {
    if (count.getAndIncrement() < 3) {
      runRegistrationJob(LocalDateTime.now());
    } else {
      scheduler.shutdown();
    }
  }
}
