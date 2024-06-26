package org.fp024.study.spring5recipes.caching.ehcache;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.function.Supplier;
import org.ehcache.expiry.ExpiryPolicy;

public class CacheExpiry implements ExpiryPolicy<String, BigDecimal> {

  @Override
  public Duration getExpiryForCreation(String key, BigDecimal value) {
    return Duration.of(3600, ChronoUnit.SECONDS);
  }

  @Override
  public Duration getExpiryForAccess(String key, Supplier<? extends BigDecimal> value) {
    return Duration.of(600, ChronoUnit.SECONDS);
  }

  @Override
  public Duration getExpiryForUpdate(
      String key, Supplier<? extends BigDecimal> oldValue, BigDecimal newValue) {
    return Duration.of(600, ChronoUnit.SECONDS);
  }
}
