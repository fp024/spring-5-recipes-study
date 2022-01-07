package org.fp024.study.spring5recipes.calculator;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class CalculatorLoggingAspect {

  @Around("execution(* *.*(..))")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

    LOGGER.info(
        "The method {}() begins with {}",
        joinPoint.getSignature().getName(),
        Arrays.toString(joinPoint.getArgs()));

    try {
      Object result = joinPoint.proceed();
      LOGGER.info("The method {}() ends with ", joinPoint.getSignature().getName(), result);
      return result;
    } catch (IllegalArgumentException e) {
      LOGGER.error(
          "Illegal argument {} in {}()",
          Arrays.toString(joinPoint.getArgs()),
          joinPoint.getSignature().getName());
      throw e;
    }
  }
}
