package org.fp024.study.spring5recipes.board.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.nio.charset.StandardCharsets;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(
    value = "org.fp024.study.spring5recipes.board",
    excludeFilters = {
      @Filter(classes = Controller.class),
      @Filter(classes = Configuration.class),
    })
@EnableTransactionManagement
@PropertySource("classpath:database.properties")
@Configuration
public class TodoRootConfig {
  private final Environment env;

  public TodoRootConfig(Environment env) {
    this.env = env;
  }

  @Bean(destroyMethod = "close")
  HikariDataSource dataSource() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(env.getProperty("jdbc.driver"));
    hikariConfig.setJdbcUrl(env.getProperty("jdbc.url"));
    hikariConfig.setUsername(env.getProperty("jdbc.username"));
    hikariConfig.setPassword(env.getProperty("jdbc.password"));
    HikariDataSource dataSource = new HikariDataSource(hikariConfig);
    databasePopulator(dataSource);
    return dataSource;
  }

  void databasePopulator(DataSource dataSource) {
    ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
    databasePopulator.setSqlScriptEncoding(StandardCharsets.UTF_8.name());
    databasePopulator.setContinueOnError(false);
    databasePopulator.setIgnoreFailedDrops(false);
    databasePopulator.addScripts(
        new ClassPathResource("/sql/schema.sql"),
        new ClassPathResource("/sql/data.sql"),
        // spring-security-acl 모듈에 포함된 sql 스크립트 실행
        new ClassPathResource("/createAclSchema.sql"));
    databasePopulator.execute(dataSource);
  }

  @Bean
  DataSourceTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }
}
