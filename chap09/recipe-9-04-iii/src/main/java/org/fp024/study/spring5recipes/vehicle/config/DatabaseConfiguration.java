package org.fp024.study.spring5recipes.vehicle.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

abstract class DatabaseConfiguration {

  protected abstract Environment getEnv();

  @Bean(destroyMethod = "close")
  HikariDataSource dataSource() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(getEnv().getProperty("jdbc.driver"));
    hikariConfig.setJdbcUrl(getEnv().getProperty("jdbc.url"));
    hikariConfig.setUsername(getEnv().getProperty("jdbc.username"));
    hikariConfig.setPassword(getEnv().getProperty("jdbc.password"));
    hikariConfig.setMinimumIdle(2);
    hikariConfig.setMaximumPoolSize(5);
    return new HikariDataSource(hikariConfig);
  }

  @Bean
  ResourceDatabasePopulator databasePopulator() {
    ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
    databasePopulator.setSqlScriptEncoding(StandardCharsets.UTF_8.name());
    databasePopulator.setContinueOnError(false);
    databasePopulator.addScript(
        new ClassPathResource(
            "sql/%s/schema.sql".formatted(getEnv().getProperty("jdbc.database.type"))));

    return databasePopulator;
  }
}
