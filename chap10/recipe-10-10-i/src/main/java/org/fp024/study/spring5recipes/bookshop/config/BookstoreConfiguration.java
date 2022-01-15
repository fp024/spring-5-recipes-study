package org.fp024.study.spring5recipes.bookshop.config;

import javax.sql.DataSource;
import org.fp024.study.spring5recipes.bookshop.BookShop;
import org.fp024.study.spring5recipes.bookshop.BookShopCashier;
import org.fp024.study.spring5recipes.bookshop.JdbcBookShop;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EnableAspectJAutoProxy
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

  @Bean
  public DriverManagerDataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(jdbcDriverName);
    dataSource.setUrl(jdbcUrl);
    dataSource.setUsername(jdbcUserName);
    dataSource.setPassword(jdbcPassword);
    return dataSource;
  }

  @Bean
  public DataSourceTransactionManager transactionManager(DataSource dataSource) {
    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
    transactionManager.setDataSource(dataSource());
    return transactionManager;
  }

  @Bean
  public JdbcBookShop bookShop(DataSource dataSource) {
    JdbcBookShop bookShop = new JdbcBookShop();
    bookShop.setDataSource(dataSource);
    return bookShop;
  }

  @Bean
  public BookShopCashier cashier(BookShop bookShop) {
    BookShopCashier cashier = new BookShopCashier();
    cashier.setBookShop(bookShop);
    return cashier;
  }
}
