package org.fp024.study.spring5recipes.caching.config;

import java.util.Objects;
import org.fp024.study.spring5recipes.caching.CalculationService;
import org.fp024.study.spring5recipes.caching.PlainCachingCalculationService;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class CalculationConfiguration {

  @Bean
  EhCacheManagerFactoryBean cacheManager() {
    EhCacheManagerFactoryBean factory = new EhCacheManagerFactoryBean();
    factory.setConfigLocation(new ClassPathResource("ehcache.xml"));
    return factory;
  }

  @Bean
  EhCacheFactoryBean calculationsCache() {
    EhCacheFactoryBean factory = new EhCacheFactoryBean();
    factory.setCacheManager(Objects.requireNonNull(cacheManager().getObject()));
    factory.setCacheName("calculations");
    return factory;
  }

  @Bean
  CalculationService calculationService() {
    return new PlainCachingCalculationService(calculationsCache().getObject());
  }
}
