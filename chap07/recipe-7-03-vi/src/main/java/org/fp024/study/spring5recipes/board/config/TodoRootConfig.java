package org.fp024.study.spring5recipes.board.config;

import static org.fp024.study.spring5recipes.board.common.Constants.PROJECT_ENCODING_VALUE;

import javax.sql.DataSource;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(
    value = "org.fp024.study.spring5recipes.board",
    excludeFilters = {
      @Filter(classes = Controller.class),
      @Filter(classes = Configuration.class),
    })
@EnableTransactionManagement
@Configuration
public class TodoRootConfig {

  @Bean
  EhCacheCacheManager cacheManager() {
    EhCacheCacheManager cacheManager = new EhCacheCacheManager();
    cacheManager.setCacheManager(ehCacheManagerFactoryBean().getObject());
    return cacheManager;
  }

  @Bean
  EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {

    EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
    return factoryBean;
  }

  @Bean
  DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.HSQL)
        .setScriptEncoding(PROJECT_ENCODING_VALUE)
        .setName("board")
        .addScripts("classpath:/schema.sql", "classpath:/data.sql")
        .build();
  }

  @Bean
  TransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }
}
