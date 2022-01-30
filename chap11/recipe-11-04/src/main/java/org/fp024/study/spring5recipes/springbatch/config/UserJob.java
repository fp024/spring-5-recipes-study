package org.fp024.study.spring5recipes.springbatch.config;

import javax.sql.DataSource;
import org.fp024.study.spring5recipes.springbatch.UserRegistration;
import org.fp024.study.spring5recipes.springbatch.UserRegistrationValidationItemProcessor;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class UserJob {
  private static final String INSERT_REGISTRATION_QUERY =
      "insert into USER_REGISTRATION (FIRST_NAME, LAST_NAME, COMPANY,"
          + " ADDRESS,CITY,STATE,ZIP,COUNTY,URL,PHONE_NUMBER,FAX) values "
          + "(:firstName,:lastName,:company,:address,:city,:state,:zip,:county,:url,:phoneNumber,:fax)";

  @Autowired private JobBuilderFactory jobs;

  @Autowired private StepBuilderFactory steps;

  @Autowired private DataSource dataSource;

  @Value("file:csv/registrations.csv")
  private Resource input;

  @Bean
  public Job insertIntoDbFromCsvJob() {
    return jobs.get("User Registration Import Job").start(step1()).build();
  }

  @Bean
  public Step step1() {
    return steps
        .get("User Registration CSV To DB Step")
        .<UserRegistration, UserRegistration>chunk(5)
        .reader(csvFileReader())
        .processor(userRegistrationValidationItemProcessor()) // 이번 예제의 주제, Processor 정의
        .writer(jdbcItemWriter())
        .build();
  }

  @Bean
  public UserRegistrationValidationItemProcessor userRegistrationValidationItemProcessor() {
    return new UserRegistrationValidationItemProcessor();
  }

  @Bean
  public FlatFileItemReader<UserRegistration> csvFileReader() {
    FlatFileItemReader<UserRegistration> itemReader = new FlatFileItemReader<>();
    itemReader.setLineMapper(lineMapper());
    itemReader.setResource(input);
    return itemReader;
  }

  @Bean
  public JdbcBatchItemWriter<UserRegistration> jdbcItemWriter() {
    JdbcBatchItemWriter<UserRegistration> itemWriter = new JdbcBatchItemWriter<>();
    itemWriter.setDataSource(dataSource);
    itemWriter.setSql(INSERT_REGISTRATION_QUERY);
    itemWriter.setItemSqlParameterSourceProvider(
        new BeanPropertyItemSqlParameterSourceProvider<>());
    return itemWriter;
  }

  @Bean
  public DefaultLineMapper<UserRegistration> lineMapper() {
    DefaultLineMapper<UserRegistration> lineMapper = new DefaultLineMapper<>();
    lineMapper.setLineTokenizer(tokenizer());
    lineMapper.setFieldSetMapper(fieldSetMapper());
    return lineMapper;
  }

  @Bean
  public BeanWrapperFieldSetMapper<UserRegistration> fieldSetMapper() {
    BeanWrapperFieldSetMapper<UserRegistration> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
    fieldSetMapper.setTargetType(UserRegistration.class);
    return fieldSetMapper;
  }

  @Bean
  public DelimitedLineTokenizer tokenizer() {
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
