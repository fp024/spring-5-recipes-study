package org.fp024.study.spring5recipes.springbatch.config;

import javax.sql.DataSource;
import org.fp024.study.spring5recipes.springbatch.UserRegistration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;

@Configuration
public class UserJob {
  private static final String INSERT_REGISTRATION_QUERY =
      "INSERT INTO USER_REGISTRATION (FIRST_NAME, LAST_NAME, COMPANY,"
          + " ADDRESS,CITY,STATE,ZIP,COUNTY,URL,PHONE_NUMBER,FAX) VALUES "
          + "(:firstName,:lastName,:company,:address,:city,:state,:zip,:county,:url,:phoneNumber,:fax)";

  @Autowired private JobBuilderFactory jobs;

  @Autowired private StepBuilderFactory steps;

  @Autowired private DataSource dataSource;

  @Value("file:./csv/registrations.csv")
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
        .writer(jdbcItemWriter())
        .build();
  }

  @Bean
  public FlatFileItemReader<UserRegistration> csvFileReader() {
    return new FlatFileItemReaderBuilder<UserRegistration>()
        .name(ClassUtils.getShortName(FlatFileItemReader.class))
        .resource(input)
        .targetType(UserRegistration.class)
        .delimited()
        .names(
            new String[] {
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
              "fax"
            })
        .build();
  }

  @Bean
  public JdbcBatchItemWriter<UserRegistration> jdbcItemWriter() {
    return new JdbcBatchItemWriterBuilder<UserRegistration>()
        .dataSource(dataSource)
        .sql(INSERT_REGISTRATION_QUERY)
        .beanMapped()
        .build();
  }
}
