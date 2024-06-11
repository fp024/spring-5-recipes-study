package org.fp024.study.spring5recipes.caching.config;

import org.fp024.study.spring5recipes.caching.CalculationService;
import org.fp024.study.spring5recipes.caching.PlainCachingCalculationService;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CalculationConfiguration {

  @Bean
  CacheManager cacheManager() {
    return new ConcurrentMapCacheManager();
  }

  @Bean
  CalculationService calculationService() {
    return new PlainCachingCalculationService(cacheManager().getCache("calculations"));
  }
}
