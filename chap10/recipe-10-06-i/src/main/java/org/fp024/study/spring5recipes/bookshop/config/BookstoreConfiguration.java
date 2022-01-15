package org.fp024.study.spring5recipes.bookshop.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.nio.charset.StandardCharsets;
import javax.sql.DataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.fp024.study.spring5recipes.bookshop.BookShop;
import org.fp024.study.spring5recipes.bookshop.BookShopCashier;
import org.fp024.study.spring5recipes.bookshop.Cashier;
import org.fp024.study.spring5recipes.bookshop.JdbcBookShop;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
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
