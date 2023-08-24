package org.fp024.study.spring5recipes.springbatch.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@EnableBatchProcessing
@ComponentScan("org.fp024.study.spring5recipes.springbatch")
@PropertySource("classpath:database.properties")
public class BatchConfiguration {
  @Autowired private Environment env;

  @Bean(destroyMethod = "close")
  public DataSource dataSource() {
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
}
