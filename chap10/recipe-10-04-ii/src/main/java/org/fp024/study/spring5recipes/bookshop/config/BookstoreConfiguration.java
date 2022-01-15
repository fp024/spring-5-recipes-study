package org.fp024.study.spring5recipes.bookshop.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.nio.charset.StandardCharsets;
import javax.sql.DataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.fp024.study.spring5recipes.bookshop.TransactionalJdbcBookShop;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

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
  public DataSource dataSource() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(jdbcDriverName);
    hikariConfig.setJdbcUrl(jdbcUrl);
    hikariConfig.setUsername(jdbcUserName);
    hikariConfig.setPassword(jdbcPassword);
    DataSource dataSource = new HikariDataSource(hikariConfig);

    // 데이터베이스 초기화에 mybatis의 ScriptRunner 클래스를 사용했다.
    try {
      ScriptRunner scriptRunner = new ScriptRunner(dataSource.getConnection());
      Resources.setCharset(StandardCharsets.UTF_8);
      scriptRunner.runScript(Resources.getResourceAsReader("sql/init-sql.sql"));
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
    return dataSource;
  }

  @Bean
  public DataSourceTransactionManager transactionManager(DataSource dataSource) {
    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
    transactionManager.setDataSource(dataSource);
    return transactionManager;
  }

  @Bean
  public TransactionTemplate transactionTemplate(
      PlatformTransactionManager transactionManager, DataSource dataSource) {
    TransactionTemplate transactionTemplate = new TransactionTemplate();
    transactionTemplate.setTransactionManager(transactionManager);
    return transactionTemplate;
  }

  @Bean
  public TransactionalJdbcBookShop bookShop(
      DataSource dataSource, TransactionTemplate transactionTemplate) {
    TransactionalJdbcBookShop bookShop = new TransactionalJdbcBookShop();
    bookShop.setDataSource(dataSource);
    bookShop.setTransactionTemplate(transactionTemplate);
    return bookShop;
  }
}
