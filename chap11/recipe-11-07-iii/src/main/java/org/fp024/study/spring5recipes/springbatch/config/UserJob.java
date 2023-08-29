package org.fp024.study.spring5recipes.springbatch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.step.tasklet.TaskletStep;
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
    return jobs //
        .get("nightlyRegistrationJob")
        .start(loadRegistration())
        .on("COMPLETED")
        .to(asyncFlow())
        .from(loadRegistration())
        .on("FAILED")
        .to(failedStep())
        .end()
        .build();
  }

  @Bean
  Flow asyncFlow() {
    return new FlowBuilder<Flow>("asyncFlow")
        .split(new SimpleAsyncTaskExecutor())
        .add(
            new FlowBuilder<Flow>("reportStatisticsFlow").from(reportStatistics()).end(),
            new FlowBuilder<Flow>("resultToQueueFlow").from(resultToQueue()).end())
        .build();
  }

  @Bean
  Step failedStep() {
    return steps
        .get("failedStep")
        .tasklet(
            (contribution, chunkContext) -> {
              LOGGER.info("### 👺 실패 처리 테스크 ###");
              return RepeatStatus.FINISHED;
            })
        .build();
  }

  @Bean
  Step resultToQueue() {
    return steps //
        .get("resultToQueue")
        .tasklet(
            (contribution, chunkContext) -> {
              Thread.sleep(2000);
              LOGGER.info("### 🎈 DB로 부터 회원 데이터 읽어서, 메시지 큐 전달 ###");
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
              LOGGER.info("### 🎈 DB로 부터 회원 데이터 읽어서, 통계 생성 ###");
              return RepeatStatus.FINISHED;
            })
        .build();
  }

  @Bean
  Step loadRegistration() {
    TaskletStep step =
        steps
            .get("User Registration CSV To DB Step")
            .tasklet(
                (contribution, chunkContext) -> {
                  LOGGER.info("### 🥲 CSV로 부터 데이터 읽어서 DB에 저장 중... 예외 발생  ###");

                  throw new IllegalStateException("임의 예외");
                })
            .build();
    LOGGER.info("### step hash: {} ", step.hashCode());
    return step;
  }
}
