package org.fp024.study.spring5recipes.springbatch.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
@PropertySource("classpath:database.properties")
public class BatchConfiguration {
  private final Environment env;

  @Bean(destroyMethod = "close")
  DataSource dataSource() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(env.getProperty("jdbc.driver"));
    hikariConfig.setJdbcUrl(env.getProperty("jdbc.url"));
    hikariConfig.setUsername(env.getProperty("jdbc.username"));
    hikariConfig.setPassword(env.getProperty("jdbc.password"));
    return new HikariDataSource(hikariConfig);
  }

  @Bean
  DataSourceInitializer dataSourceInitializer() {
    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource());
    // 데이터 베이스 채우기
    initializer.setDatabasePopulator(databasePopulator());
    return initializer;
  }

  private DatabasePopulator databasePopulator() {
    ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
    // 쿼리 실행에 에러가 있더라도 진행할지 여부
    databasePopulator.setContinueOnError(true);
    databasePopulator.addScript(
        new ClassPathResource("org/springframework/batch/core/schema-drop-hsqldb.sql"));
    databasePopulator.addScript(
        new ClassPathResource("org/springframework/batch/core/schema-hsqldb.sql"));
    databasePopulator.addScript(new ClassPathResource("sql/reset_user_registration.sql"));
    return databasePopulator;
  }

  @Bean
  PlatformTransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource());
  }

  @Bean
  JobRepositoryFactoryBean jobRepository() {
    // Repository를 구현한 객체, Job과 Step을 아울러 도메인 모델에 관한 조회 / 저장 작업을 처리
    JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
    jobRepositoryFactoryBean.setDataSource(dataSource());
    jobRepositoryFactoryBean.setTransactionManager(transactionManager());
    return jobRepositoryFactoryBean;
  }

  @Bean
  JobLauncher jobLauncher() throws Exception {
    // 배치 Job을 시동하는 메커니즘을 건내줌, jobLauncher를 사용해 실행할 배치 솔루션명과 필요한 매게변수를 지정
    SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
    jobLauncher.setJobRepository(jobRepository().getObject());
    return jobLauncher;
  }

  @Bean
  JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() {
    // 스프링 컨텍스트 파일을 스캐닝해 구성된 Job이 발견되면,
    // 모두 MapJobRegistry에 엮는 빈 후처리기
    JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
    jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry());
    return jobRegistryBeanPostProcessor;
  }

  @Bean
  JobRegistry jobRegistry() {
    // 특정 Job에 대한 정보를 담고 있는 중앙저장소
    return new MapJobRegistry();
  }
}
