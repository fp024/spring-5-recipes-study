package org.fp024.study.spring5recipes.springbatch.config;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.springbatch.UserRegistration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
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
  private static final String INSERT_REGISTRATION_QUERY =
      "insert into USER_REGISTRATION (FIRST_NAME, LAST_NAME, COMPANY,"
          + " ADDRESS,CITY,STATE,ZIP,COUNTY,URL,PHONE_NUMBER,FAX) values "
          + "(:firstName,:lastName,:company,:address,:city,:state,:zip,:county,:url,:phoneNumber,:fax)";

  private final JobBuilderFactory jobs;

  private final StepBuilderFactory steps;

  private final DataSource dataSource;

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
        .<UserRegistration, UserRegistration>chunk(5)
        // 첫 스탭의 오류 허용, 그 후 제한 횟수 및 재시도 대상 예외 지정
        .faultTolerant()
        .retryLimit(3)
        .retry(DeadlockLoserDataAccessException.class)
        .reader(csvFileReader())
        .processor(new DeadlockTestProcessor())
        .writer(jdbcItemWriter())
        .build();
  }

  @Bean
  FlatFileItemReader<UserRegistration> csvFileReader() {
    FlatFileItemReader<UserRegistration> itemReader = new FlatFileItemReader<>();
    itemReader.setLineMapper(lineMapper());
    itemReader.setResource(input);
    return itemReader;
  }

  @Bean
  JdbcBatchItemWriter<UserRegistration> jdbcItemWriter() {
    JdbcBatchItemWriter<UserRegistration> itemWriter = new JdbcBatchItemWriter<>();
    itemWriter.setDataSource(dataSource);
    itemWriter.setSql(INSERT_REGISTRATION_QUERY);
    itemWriter.setItemSqlParameterSourceProvider(
        new BeanPropertyItemSqlParameterSourceProvider<>());
    return itemWriter;
  }

  @Bean
  DefaultLineMapper<UserRegistration> lineMapper() {
    DefaultLineMapper<UserRegistration> lineMapper = new DefaultLineMapper<>();
    lineMapper.setLineTokenizer(tokenizer());
    lineMapper.setFieldSetMapper(fieldSetMapper());
    return lineMapper;
  }

  @Bean
  BeanWrapperFieldSetMapper<UserRegistration> fieldSetMapper() {
    BeanWrapperFieldSetMapper<UserRegistration> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
    fieldSetMapper.setTargetType(UserRegistration.class);
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
