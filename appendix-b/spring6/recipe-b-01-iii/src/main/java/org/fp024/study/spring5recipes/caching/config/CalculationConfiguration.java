package org.fp024.study.spring5recipes.caching.config;

import java.io.IOException;
import java.math.BigDecimal;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;
import org.ehcache.xml.exceptions.XmlConfigurationException;
import org.fp024.study.spring5recipes.caching.CalculationService;
import org.fp024.study.spring5recipes.caching.PlainCachingCalculationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class CalculationConfiguration {

  @Bean(destroyMethod = "close")
  CacheManager cacheManager() {
    try {
      org.ehcache.config.Configuration xmlConfig =
          new XmlConfiguration(new ClassPathResource("ehcache.xml").getURL());
      CacheManager cacheManager = CacheManagerBuilder.newCacheManager(xmlConfig);
      cacheManager.init();
      return cacheManager;

    } catch (XmlConfigurationException | IOException e) {
      throw new IllegalStateException(e);
    }
  }

  @Bean
  public CalculationService calculationService() {
    Cache<String, BigDecimal> cache =
        cacheManager().getCache("calculations", String.class, BigDecimal.class);
    return new PlainCachingCalculationService(cache);
  }
}
