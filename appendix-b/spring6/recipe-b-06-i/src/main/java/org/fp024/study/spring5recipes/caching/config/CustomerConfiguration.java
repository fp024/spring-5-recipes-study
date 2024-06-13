package org.fp024.study.spring5recipes.caching.config;

import java.io.IOException;
import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.jcache.JCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@EnableCaching
public class CustomerConfiguration {

  @Bean
  CacheManager cacheManager() {
    return new JCacheCacheManager(Objects.requireNonNull(jCacheManagerFactoryBean().getObject()));
  }

  @Bean
  JCacheManagerFactoryBean jCacheManagerFactoryBean() {
    try {
      JCacheManagerFactoryBean factory = new JCacheManagerFactoryBean();
      factory.setCacheManagerUri(new ClassPathResource("ehcache.xml").getURI());
      return factory;
    } catch (IOException e) {
      throw new IllegalStateException("Failed to load Ehcache configuration", e);
    }
  }

  @Bean
  DataSourceTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  @Bean
  DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .setName("customers")
        .addScript("classpath:/schema.sql")
        .build();
  }
}
