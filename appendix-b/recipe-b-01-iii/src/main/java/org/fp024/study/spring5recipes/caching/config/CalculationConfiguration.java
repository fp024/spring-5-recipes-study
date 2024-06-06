package org.fp024.study.spring5recipes.caching.config;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.fp024.study.spring5recipes.caching.CalculationService;
import org.fp024.study.spring5recipes.caching.PlainCachingCalculationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CalculationConfiguration {

  @Bean
  CacheManager cacheManager() {
    return CacheManager.getInstance();
  }

  @Bean
  public CalculationService calculationService() {
    Cache cache = cacheManager().getCache("calculations");
    return new PlainCachingCalculationService(cache);
  }
}
