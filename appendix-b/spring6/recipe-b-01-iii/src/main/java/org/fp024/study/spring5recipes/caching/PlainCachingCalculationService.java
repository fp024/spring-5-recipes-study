package org.fp024.study.spring5recipes.caching;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;

@Slf4j
public class PlainCachingCalculationService implements CalculationService {
  private final Cache<String, BigDecimal> cache;

  public PlainCachingCalculationService(Cache<String, BigDecimal> cache) {
    this.cache = cache;
  }

  @Override
  public BigDecimal heavyCalculation(BigDecimal base, int power) {
    String key = base + "^" + power;
    BigDecimal result = cache.get(key);
    if (result != null) {
      return result;
    }

    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      LOGGER.warn("Interrupted!", e);
      /* 중단하기 전에 처리해야 하는 모든 작업을 정리.  */
      Thread.currentThread().interrupt();
    }

    BigDecimal calculatedResult = base.pow(power);
    cache.putIfAbsent(key, calculatedResult);
    return calculatedResult;
  }
}
