package org.fp024.study.spring5recipes.caching.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class CalculationConfiguration {

  @Bean
  CacheManager cacheManager() {
    EhCacheCacheManager cacheManager = new EhCacheCacheManager();
    cacheManager.setCacheManager(ehCacheManagerFactoryBean().getObject());
    return cacheManager;
  }

  @Bean
  EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
    EhCacheManagerFactoryBean factory = new EhCacheManagerFactoryBean();
    factory.setConfigLocation(new ClassPathResource("ehcache.xml"));
    return factory;
  }
}
