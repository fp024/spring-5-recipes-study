package org.fp024.study.spring5recipes.caching;

import java.lang.reflect.Method;
import org.springframework.cache.interceptor.KeyGenerator;

public class CustomKeyGenerator implements KeyGenerator {
  @Override
  public Object generate(Object target, Method method, Object... params) {
    return params[0] + "^" + params[1];
  }
}
