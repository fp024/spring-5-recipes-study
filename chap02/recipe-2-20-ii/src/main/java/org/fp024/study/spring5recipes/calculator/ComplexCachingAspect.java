package org.fp024.study.spring5recipes.calculator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ComplexCachingAspect {
  private final Map<String, Complex> cache = new ConcurrentHashMap<>();

  // 처음 만든 복소수는 캐시 맵에 넣고,
  // 이미 만든 복소수는 캐시 맵에서 빼와서 재사용한다.
  @Around("call(public Complex.new(int, int)) && args(a, b)")
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
