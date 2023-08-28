package org.fp024.study.spring5recipes.springbatch.config;

import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.springbatch.outer.dto.UserRegistrationDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;

@RequiredArgsConstructor
@Configuration
public class UserJob {
  private final JobBuilderFactory jobs;

  private final StepBuilderFactory steps;

  private final UserRegistrationServiceItemWriter userRegistrationServiceItemWriter;

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
