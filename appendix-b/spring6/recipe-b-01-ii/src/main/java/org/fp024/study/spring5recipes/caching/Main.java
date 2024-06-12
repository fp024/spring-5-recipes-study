package org.fp024.study.spring5recipes.caching;

import java.math.BigDecimal;
import java.net.URL;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;

public class Main {
  public static void main(String[] args) {

    URL ehcacheXml = Main.class.getResource("/ehcache.xml");
    Configuration xmlConfig = new XmlConfiguration(ehcacheXml);

    try (CacheManager cacheManager = CacheManagerBuilder.newCacheManager(xmlConfig)) {
      cacheManager.init();

      Cache<String, BigDecimal> cache =
          cacheManager.getCache("calculations", String.class, BigDecimal.class);
      CalculationService calculationService = new PlainCachingCalculationService(cache);

      for (int i = 0; i < 10; i++) {
        long start = System.currentTimeMillis();
        System.out.println(calculationService.heavyCalculation(BigDecimal.valueOf(2L), 16));
        long duration = System.currentTimeMillis() - start;
        System.out.println("Took: " + duration);
      }
    }
  }
}
