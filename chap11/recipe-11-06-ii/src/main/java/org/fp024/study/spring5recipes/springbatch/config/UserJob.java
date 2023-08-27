package org.fp024.study.spring5recipes.springbatch.config;

import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.springbatch.outer.dto.UserRegistrationDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.dao.DeadlockLoserDataAccessException;

@RequiredArgsConstructor
@Configuration
public class UserJob {
  private final JobBuilderFactory jobs;

  private final StepBuilderFactory steps;

  private final RetryableUserRegistrationServiceItemWriter retryableItemWriter;

  @Value("file:csv/registrations.csv")
  private final Resource input;

  @Bean
  Job insertIntoDbFromCsvJob() {
    return jobs //
        .get("User Registration Import Job")
        .start(step1())
        .build();
  }

  @Bean
  Step step1() {
    return steps
        .get("User Registration CSV To DB Step")
        .<UserRegistrationDTO, UserRegistrationDTO>chunk(5)
        // 첫 스탭의 오류 허용, 그 후 제한 횟수 및 재시도 대상 예외 지정
        .faultTolerant()
        .retryLimit(3)
        .retry(DeadlockLoserDataAccessException.class)
        .reader(csvFileReader())
        .processor(new DeadlockTestProcessor())
        .writer(retryableItemWriter)
        .build();
  }

  @Bean
  FlatFileItemReader<UserRegistrationDTO> csvFileReader() {
    FlatFileItemReader<UserRegistrationDTO> itemReader = new FlatFileItemReader<>();
    itemReader.setLineMapper(lineMapper());
    itemReader.setResource(input);
    return itemReader;
  }

  @Bean
  DefaultLineMapper<UserRegistrationDTO> lineMapper() {
    DefaultLineMapper<UserRegistrationDTO> lineMapper = new DefaultLineMapper<>();
    lineMapper.setLineTokenizer(tokenizer());
    lineMapper.setFieldSetMapper(fieldSetMapper());
    return lineMapper;
  }

  @Bean
  BeanWrapperFieldSetMapper<UserRegistrationDTO> fieldSetMapper() {
    BeanWrapperFieldSetMapper<UserRegistrationDTO> fieldSetMapper =
        new BeanWrapperFieldSetMapper<>();
    fieldSetMapper.setTargetType(UserRegistrationDTO.class);
    return fieldSetMapper;
  }

  @Bean
  DelimitedLineTokenizer tokenizer() {
    DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
    tokenizer.setDelimiter(",");
    tokenizer.setNames(
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
        "fax");
    return tokenizer;
  }
}
