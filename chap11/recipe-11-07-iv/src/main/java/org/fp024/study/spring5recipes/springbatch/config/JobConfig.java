package org.fp024.study.spring5recipes.springbatch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JobConfig {
  private final JobBuilderFactory jobs;

  private final StepBuilderFactory steps;

  @Bean
  Job job1() {
    JobBuilder builder =
        jobs //
            .get("job1");

    return builder
        .start(step1())
        .next(horoscopeDecider())
        .on("MERCURY_IN_RETROGRADE")
        .to(step2())
        .from(horoscopeDecider())
        .on("COMPLETED")
        .to(step3())
        .end()
        .build();
  }

  @Bean
  Step step1() {
    return steps
        .get("step1") //
        .tasklet(
            (contribution, chunkContext) -> {
              LOGGER.info("### ✨Step 1 ###");
              return RepeatStatus.FINISHED;
            })
        .build();
  }

  @Bean
  Step step2() {
    return steps
        .get("step2") //
        .tasklet(
            (contribution, chunkContext) -> {
              LOGGER.info("### ✨Step 2 ###");
              return RepeatStatus.FINISHED;
            })
        .build();
  }

  @Bean
  Step step3() {
    return steps
        .get("step3") //
        .tasklet(
            (contribution, chunkContext) -> {
              LOGGER.info("### ✨Step 3 ###");
              return RepeatStatus.FINISHED;
            })
        .build();
  }

  @Bean
  HoroscopeDecider horoscopeDecider() {
    return new HoroscopeDecider();
  }
}
