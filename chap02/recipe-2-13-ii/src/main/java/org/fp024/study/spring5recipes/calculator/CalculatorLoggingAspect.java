package org.fp024.study.spring5recipes.calculator;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class CalculatorLoggingAspect {

  @Before("execution(* *.*(..))")
  public void logBefore(JoinPoint joinPoint) {
    LOGGER.info(
        "The method {}() begins with {} ",
        joinPoint.getSignature().getName(),
        Arrays.toString(joinPoint.getArgs()));
  }

  @After("execution(* *.*(..))")
  public void logAfter(JoinPoint joinPoint) {
    LOGGER.info("The method {}() ends", joinPoint.getSignature().getName());
  }
}
