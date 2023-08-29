package org.fp024.study.spring5recipes.springbatch.config;

import java.time.LocalDate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.springbatch.outer.dto.UserRegistrationDTO;
import org.fp024.study.spring5recipes.springbatch.outer.service.UserRegistrationService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class UserJob {
  private final JobBuilderFactory jobs;

  private final StepBuilderFactory steps;

  private final UserRegistrationServiceItemWriter userRegistrationServiceItemWriter;

  private final UserRegistrationService userRegistrationService;

  @Value("file:csv/registrations.csv")
  private final Resource input;

  @Bean
  Job nightlyRegistrationJob() {
    return jobs //
        .get("nightlyRegistrationJob")
        .start(loadRegistration())
        .next(reportStatistics())
        .next(resultToQueue())
        .build();
  }

  private Step resultToQueue() {
    return steps //
        .get("resultToQueue")
        .tasklet(
            (contribution, chunkContext) -> {
              // 여기서도 회원 데이터를 읽고 등록 성공한 경우에 대해서 메시지 큐전달인데...
              // 원직적으로는 DB에서 등록이 성공한 내용을 새로 읽어야함.
              // ✨ 스프링 배치에서는 스텝간 데이터 공유가 권장되지 않음. ✨
              LOGGER.info("### 메시지 큐 전달 ###");
              return RepeatStatus.FINISHED;
            })
        .build();
  }

  @Bean
  Step reportStatistics() {
    return steps //
        .get("reportStatistics")
        .<UserRegistrationDTO, UserRegistrationDTO>chunk(5)
        .reader(new UserRegistrationServiceItemReader(userRegistrationService, LocalDate.now()))
        .writer(
            items ->
                LOGGER.info(
                    "### {} 회원들의 월별 지표 처리 ###",
                    items.stream()
                        .map(UserRegistrationDTO::getFirstName)
                        .collect(Collectors.joining(","))))
        .build();
  }

  @Bean
  Step loadRegistration() {
    return steps
        .get("User Registration CSV To DB Step")
        .<UserRegistrationDTO, UserRegistrationDTO>chunk(5)
        .reader(csvFileReader())
        .writer(userRegistrationServiceItemWriter)
        .build();
  }

  @Bean
  FlatFileItemReader<UserRegistrationDTO> csvFileReader() {
    return new FlatFileItemReaderBuilder<UserRegistrationDTO>()
        .name(ClassUtils.getShortName(FlatFileItemReader.class))
        .resource(input)
        .targetType(UserRegistrationDTO.class)
        .delimited()
        .names(
            "firstName",
            "lastName",
            "company",
            "address",
            "city",
            "state",
            "zip",
            "county",
            "url",
            "phoneNumber",
            "fax")
        .build();
  }
}
