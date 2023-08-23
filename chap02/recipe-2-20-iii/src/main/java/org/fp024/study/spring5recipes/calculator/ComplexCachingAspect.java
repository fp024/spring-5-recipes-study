package org.fp024.study.spring5recipes.calculator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class ComplexCachingAspect {

  private final Map<String, Complex> cache;

  public ComplexCachingAspect() {
    LOGGER.info("### ComplexCachingAspect() ###");
    cache = Collections.synchronizedMap(new HashMap<String, Complex>());
  }

  @Around("call(public Complex.new(int, int)) && args(a,b)")
  public Object cacheAround(ProceedingJoinPoint joinPoint, int a, int b) throws Throwable {

    String key = a + "," + b;
    Complex complex = cache.get(key);

    if (complex == null) {
      System.out.println("Cache MISS for (" + key + ")");
      complex = (Complex) joinPoint.proceed();
      cache.put(key, complex);
    } else {
      System.out.println("Cache HIT for (" + key + ")");
    }

    return complex;
  }
}
