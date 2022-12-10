package org.fp024.study.spring5recipes.bookshop.config;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import javax.sql.DataSource;
import org.fp024.study.spring5recipes.bookshop.BookShop;
import org.fp024.study.spring5recipes.bookshop.BookShopCashier;
import org.fp024.study.spring5recipes.bookshop.Cashier;
import org.fp024.study.spring5recipes.bookshop.JdbcBookShop;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:database-mysql.properties")
public class BookstoreConfiguration {
  @Value("${jdbc.driver}")
  private String jdbcDriverName;

  @Value("${jdbc.url}")
  private String jdbcUrl;

  @Value("${jdbc.username}")
  private String jdbcUserName;

  @Value("${jdbc.password}")
  private String jdbcPassword;

  // @Bean(destroyMethod = "close")
  // public DataSource dataSource() {
  //   HikariConfig hikariConfig = new HikariConfig();
  //   hikariConfig.setDriverClassName(jdbcDriverName);
  //   hikariConfig.setJdbcUrl(jdbcUrl);
  //   hikariConfig.setUsername(jdbcUserName);
  //   hikariConfig.setPassword(jdbcPassword);
  //   DataSource dataSource = new HikariDataSource(hikariConfig);

  //   runInitSqlScript(dataSource);

  //   return dataSource;
  // }

  @Bean
  public DriverManagerDataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(jdbcDriverName);
    dataSource.setUrl(jdbcUrl);
    dataSource.setUsername(jdbcUserName);
    dataSource.setPassword(jdbcPassword);

    runInitSqlScript(dataSource);
    return dataSource;
  }

  // 데이터베이스 초기화에 Spring JDBC의 ScriptUtils 클래스를 사용했다.
  private void runInitSqlScript(DataSource dataSource) {
    try (Connection connection = dataSource.getConnection()) {
      ScriptUtils.executeSqlScript(
          connection,
          new EncodedResource(
              new ClassPathResource("sql/mysql/init-sql.sql"), StandardCharsets.UTF_8),
          false,
          true,
          ScriptUtils.DEFAULT_COMMENT_PREFIX,
          ScriptUtils.DEFAULT_STATEMENT_SEPARATOR,
          ScriptUtils.DEFAULT_BLOCK_COMMENT_START_DELIMITER,
          ScriptUtils.DEFAULT_BLOCK_COMMENT_END_DELIMITER);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  @Bean
  public DataSourceTransactionManager transactionManager(DataSource dataSource) {
    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
    transactionManager.setDataSource(dataSource);
    return transactionManager;
  }

  @Bean
  public JdbcBookShop bookShop(DataSource dataSource) {
    JdbcBookShop bookShop = new JdbcBookShop();
    bookShop.setDataSource(dataSource);
    return bookShop;
  }

  @Bean
  public Cashier cashier(BookShop bookShop) {
    BookShopCashier cashier = new BookShopCashier();
    cashier.setBookShop(bookShop);
    return cashier;
  }
}
