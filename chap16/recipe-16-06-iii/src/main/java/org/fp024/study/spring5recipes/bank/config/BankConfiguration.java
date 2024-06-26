package org.fp024.study.spring5recipes.bank.config;

import javax.sql.DataSource;
import org.fp024.study.spring5recipes.bank.dao.AccountDao;
import org.fp024.study.spring5recipes.bank.dao.InMemoryAccountDao;
import org.fp024.study.spring5recipes.bank.dao.JdbcAccountDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("org.fp024.study.spring5recipes.bank")
public class BankConfiguration {

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Configuration
  @Profile("!in-mem")
  @PropertySource("classpath:/application.properties")
  public static class JdbcBankConfiguration {

    private final Environment env;

    public JdbcBankConfiguration(Environment env) {
      this.env = env;
    }

    @Bean
    public DataSource dataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setUrl(env.getProperty("jdbc.url"));
      dataSource.setUsername(env.getProperty("jdbc.username"));
      dataSource.setPassword(env.getProperty("jdbc.password"));
      return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
      return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public AccountDao accountDao(JdbcTemplate jdbcTemplate) {
      return new JdbcAccountDao(jdbcTemplate);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
      return new JdbcTemplate(dataSource);
    }
  }

  @Configuration
  @Profile("in-mem")
  public static class InMemoryBankConfiguration {

    @Bean
    public AccountDao accountDao() {
      return new InMemoryAccountDao();
    }
  }
}
