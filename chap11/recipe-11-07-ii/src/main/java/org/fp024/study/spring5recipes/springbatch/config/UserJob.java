package org.fp024.study.spring5recipes.springbatch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class UserJob {
  private final JobBuilderFactory jobs;

  private final StepBuilderFactory steps;

  @Bean
  Job nightlyRegistrationJob() {
    JobBuilder builder = jobs.get("nightlyRegistrationJob");

    Flow reportStatisticsFlow =
        new FlowBuilder<Flow>("reportStatisticsFlow").from(reportStatistics()).end();
    Flow resultToQueueFlow = //
        new FlowBuilder<Flow>("resultToQueueFlow").from(resultToQueue()).end();

    return builder //
        .start(loadRegistration())
        .split(new SimpleAsyncTaskExecutor())
        .add(reportStatisticsFlow, resultToQueueFlow)
        .end()
        .build();
  }

  Step resultToQueue() {
    return steps //
        .get("resultToQueue")
        .tasklet(
            (contribution, chunkContext) -> {
              Thread.sleep(2000);
              LOGGER.info("### DB로 부터 회원 데이터 읽어서, 메시지 큐 전달 ###");
              return RepeatStatus.FINISHED;
            })
        .build();
  }

  @Bean
  Step reportStatistics() {
    return steps //
        .get("reportStatistics")
        .tasklet(
            (contribution, chunkContext) -> {
              Thread.sleep(2000);
              LOGGER.info("### DB로 부터 회원 데이터 읽어서, 통계 생성 ###");
              return RepeatStatus.FINISHED;
            })
        .build();
  }

  @Bean
  Step loadRegistration() {
    return steps
        .get("User Registration CSV To DB Step")
        .tasklet(
            (contribution, chunkContext) -> {
              LOGGER.info("### CSV로 부터 데이터 읽어서 DB에 저장 ###");
              return RepeatStatus.FINISHED;
            })
        .build();
  }
}
