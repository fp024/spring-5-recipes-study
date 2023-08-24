package org.fp024.study.spring5recipes.bookshop.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.nio.charset.StandardCharsets;
import javax.sql.DataSource;
import org.fp024.study.spring5recipes.bookshop.TransactionalJdbcBookShop;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource("classpath:database.properties")
public class BookstoreConfiguration {
  @Value("${jdbc.driver}")
  private String jdbcDriverName;

  @Value("${jdbc.url}")
  private String jdbcUrl;

  @Value("${jdbc.username}")
  private String jdbcUserName;

  @Value("${jdbc.password}")
  private String jdbcPassword;

  @Bean(destroyMethod = "close")
  DataSource dataSource() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(jdbcDriverName);
    hikariConfig.setJdbcUrl(jdbcUrl);
    hikariConfig.setUsername(jdbcUserName);
    hikariConfig.setPassword(jdbcPassword);
    return new HikariDataSource(hikariConfig);
  }

  @Bean
  DataSourceInitializer dataSourceInitializer() {
    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource());
    initializer.setDatabasePopulator(databasePopulator());
    return initializer;
  }

  private DatabasePopulator databasePopulator() {
    ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
    databasePopulator.setSqlScriptEncoding(StandardCharsets.UTF_8.name());
    databasePopulator.setContinueOnError(false);
    databasePopulator.addScript(new ClassPathResource("sql/init-sql.sql"));
    return databasePopulator;
  }

  @Bean
  DataSourceTransactionManager transactionManager(DataSource dataSource) {
    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
    transactionManager.setDataSource(dataSource);
    return transactionManager;
  }

  @Bean
  TransactionalJdbcBookShop bookShop(
      DataSource dataSource, PlatformTransactionManager transactionManager) {
    TransactionalJdbcBookShop bookShop = new TransactionalJdbcBookShop();
    bookShop.setDataSource(dataSource);
    bookShop.setTransactionManager(transactionManager);
    return bookShop;
  }
}
