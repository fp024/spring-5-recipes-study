package org.fp024.study.spring5recipes.springbatch.config;

import javax.sql.DataSource;
import org.fp024.study.spring5recipes.springbatch.UserRegistration;
import org.fp024.study.spring5recipes.springbatch.UserRegistrationValidationItemProcessor;
import org.fp024.study.spring5recipes.springbatch.exception.InvalidDataException;
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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

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
        .<UserRegistration, UserRegistration>chunk(3)
        .faultTolerant()
        // .noRollback(InvalidDataException.class)
        .skip(InvalidDataException.class)
        .skipLimit(1) // 한번 예외는 성공으로 넘어감
        .reader(csvFileReader())
        // .readerIsTransactionalQueue() // 리더가 트랜젝션 임을 나타내는 플래그 설정
        .processor(userRegistrationValidationItemProcessor())
        .writer(jdbcItemWriter())
        .transactionManager(new DataSourceTransactionManager(dataSource))
        // 트랜젝션매니저를 분명하게 가리키고 싶을 때 추가,
        // 기본으로는 PlatformTransactionManager를 컨텍스트에서 얻어서 사용함.
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
