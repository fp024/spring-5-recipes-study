package org.fp024.study.spring5recipes.caching;

import java.math.BigDecimal;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;

public class Main {
  public static void main(String[] args) {
    CacheManager cacheManager = null;
    long totalStart = System.currentTimeMillis();
    try {
      cacheManager = CacheManager.getInstance();
      Ehcache cache = cacheManager.getEhcache("calculations");
      CalculationService calculationService = new PlainCachingCalculationService(cache);

      for (int i = 0; i < 10; i++) {
        long start = System.currentTimeMillis();
        System.out.println(calculationService.heavyCalculation(BigDecimal.valueOf(2L), 16));
        long duration = System.currentTimeMillis() - start;
        System.out.println("Took: " + duration);
      }

    } finally {
      if (cacheManager != null) {
        cacheManager.shutdown();
      }
    }
    long totalDuration = System.currentTimeMillis() - totalStart;
    System.out.println("Total Took: " + totalDuration);
  }
}
