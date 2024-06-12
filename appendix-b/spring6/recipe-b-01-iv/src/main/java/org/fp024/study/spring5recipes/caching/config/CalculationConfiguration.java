package org.fp024.study.spring5recipes.caching.config;

import java.io.IOException;
import java.util.Objects;
import org.fp024.study.spring5recipes.caching.CalculationService;
import org.fp024.study.spring5recipes.caching.PlainCachingCalculationService;
import org.springframework.cache.CacheManager;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.jcache.JCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class CalculationConfiguration {
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
  CalculationService calculationService() {
    return new PlainCachingCalculationService(cacheManager().getCache("calculations"));
  }
}
