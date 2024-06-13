package org.fp024.study.spring5recipes.caching.config.ehcache;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.function.Supplier;
import org.ehcache.expiry.ExpiryPolicy;

public class CacheExpiry implements ExpiryPolicy<Object, Object> {

  @Override
  public Duration getExpiryForCreation(Object key, Object value) {
    return Duration.of(3600, ChronoUnit.SECONDS);
  }

  @Override
  public Duration getExpiryForAccess(Object key, Supplier<? extends Object> value) {
    return Duration.of(600, ChronoUnit.SECONDS);
  }

  @Override
  public Duration getExpiryForUpdate(
      Object key, Supplier<? extends Object> oldValue, Object newValue) {
    return Duration.of(600, ChronoUnit.SECONDS);
  }
}
