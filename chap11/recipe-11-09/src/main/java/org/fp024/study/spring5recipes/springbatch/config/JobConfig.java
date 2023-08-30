package org.fp024.study.spring5recipes.springbatch.config;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
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
    return jobs //
        .get("job1")
        .start(step1(null))
        .next(step2())
        .build();
  }

  @Bean
  @JobScope
  Step step1(@Value("#{jobParameters['max.count']}") String maxCount) {
    return steps
        .get("step1") //
        .tasklet(
            (contribution, chunkContext) -> {
              Date date = (Date) chunkContext.getStepContext().getJobParameters().get("date");
              LOGGER.info("### ✨Step 1 : date: {}, max.count {} ###", date, maxCount);
              return RepeatStatus.FINISHED;
            })
        .build();
  }

  @Bean
  Step step2() {
    return steps
        .get("step2") //
        .<Long, Long>chunk(5)
        .reader(customReader(null))
        .writer(customWriter())
        .build();
  }

  @StepScope
  @Bean
  ItemReader<Long> customReader(@Value("#{jobParameters['max.count']}") Long maxCount) {
    return new ItemReader<>() {

      private Long count = 0L;

      @Override
      public Long read() throws Exception {
        return fetchData();
      }

      private Long fetchData() {
        if (maxCount > count) {
          return ++count;
        } else {
          return null;
        }
      }
    };
  }

  @Bean
  ItemWriter<Long> customWriter() {
    return items -> LOGGER.info("👺 그냥 화면 출력: {}", items);
  }
}
