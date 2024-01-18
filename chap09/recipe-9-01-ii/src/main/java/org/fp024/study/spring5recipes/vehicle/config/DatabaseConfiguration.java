package org.fp024.study.spring5recipes.vehicle.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
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

  /** test 프로필이 활성화 된상태에서는 DB 초기화를 하지 않는다. */
  @Profile("!test")
  @Bean
  DataSourceInitializer dataSourceInitializer() {
    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource());
    initializer.setDatabasePopulator(databasePopulator());
    return initializer;
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
