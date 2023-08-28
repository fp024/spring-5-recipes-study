package org.fp024.study.spring5recipes.springbatch.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.MaxAttemptsRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@RequiredArgsConstructor
@Configuration
// modular = (기본값)false : 구성이 여러 애플리케이션 컨텍스트로 모듈화되는지 여부를 나타냅니다.
// true인 경우 이 컨텍스트에서 @Bean 작업 정의를 생성해서는 안 되며 오히려
// ApplicationContextFactory를 통해 별도의(자식) 컨텍스트에서 제공해야 합니다.
@EnableBatchProcessing(modular = false)
@ComponentScan("org.fp024.study.spring5recipes.springbatch")
@PropertySource("classpath:database.properties")
public class BatchConfiguration {
  private final Environment env;

  // https://stackoverflow.com/questions/25540502/use-of-multiple-datasources-in-spring-batch
  @Bean
  BatchConfigurer configurer(@Qualifier("batchDataSource") DataSource dataSource) {
    return new DefaultBatchConfigurer(dataSource);
  }

  @Primary
  @Bean(name = "batchDataSource", destroyMethod = "close")
  HikariDataSource dataSource() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(env.getProperty("batch.jdbc.driver"));
    hikariConfig.setJdbcUrl(env.getProperty("batch.jdbc.url"));
    hikariConfig.setUsername(env.getProperty("batch.jdbc.username"));
    hikariConfig.setPassword(env.getProperty("batch.jdbc.password"));
    return new HikariDataSource(hikariConfig);
  }

  @Bean
  DataSourceInitializer batchDataSourceInitializer() {
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
    // 실패한 SQL DROP 문을 무시할 수 있음을 나타내는 플래그
    databasePopulator.setIgnoreFailedDrops(true);
    databasePopulator.addScript(
        new ClassPathResource("org/springframework/batch/core/schema-drop-hsqldb.sql"));
    databasePopulator.addScript(
        new ClassPathResource("org/springframework/batch/core/schema-hsqldb.sql"));
    return databasePopulator;
  }

  @Bean
  RetryTemplate retryTemplate() {
    RetryTemplate retryTemplate = new RetryTemplate();
    retryTemplate.setRetryPolicy(retryPolicy());
    retryTemplate.setBackOffPolicy(backOffPolicy());
    return retryTemplate;
  }

  @Bean
  MaxAttemptsRetryPolicy retryPolicy() {
    MaxAttemptsRetryPolicy retryPolicy = new MaxAttemptsRetryPolicy();
    retryPolicy.setMaxAttempts(3);
    return retryPolicy;
  }

  @Bean
  ExponentialBackOffPolicy backOffPolicy() {
    ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
    backOffPolicy.setInitialInterval(3000); // 첫 번째 시도 지연 값 1초
    backOffPolicy.setMultiplier(2); // 이후 시도할 때마다 재연이 얼마나 증가되는지 제어
    backOffPolicy.setMaxInterval(10000); // 지연 간격 10초
    // 최초는 1초만 기다리고, 두번째에는 10초, 새번째에는 증감값 고려해서 2배해서 20초 지연 감수 같음.
    return backOffPolicy;
  }
}
