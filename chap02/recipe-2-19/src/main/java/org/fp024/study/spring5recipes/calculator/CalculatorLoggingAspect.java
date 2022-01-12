package org.fp024.study.spring5recipes.calculator;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class CalculatorLoggingAspect {
  @Pointcut("execution(* *.*(..))")
  private void loggingOperation() {}

  @Before("CalculatorLoggingAspect.loggingOperation()")
  public void logBefore(JoinPoint joinPoint) {
    LOGGER.info(
        "The method "
            + joinPoint.getSignature().getName()
            + "() begins with "
            + Arrays.toString(joinPoint.getArgs()));
  }

  @After("CalculatorLoggingAspect.loggingOperation()")
  public void logAfter(JoinPoint joinPoint) {
    LOGGER.info("The method " + joinPoint.getSignature().getName() + "() ends");
  }

  @AfterReturning(pointcut = "CalculatorLoggingAspect.loggingOperation()", returning = "result")
  public void logAfterReturning(JoinPoint joinPoint, Object result) {
    LOGGER.info("The method " + joinPoint.getSignature().getName() + "() ends with " + result);
  }

  @AfterThrowing(pointcut = "CalculatorLoggingAspect.loggingOperation()", throwing = "e")
  public void logAfterThrowing(JoinPoint joinPoint, IllegalArgumentException e) {
    LOGGER.error(
        "Illegal argument "
            + Arrays.toString(joinPoint.getArgs())
            + " in "
            + joinPoint.getSignature().getName()
            + "()");
  }

  @Around("CalculatorLoggingAspect.loggingOperation()")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    LOGGER.info(
        "The method "
            + joinPoint.getSignature().getName()
            + "() begins with "
            + Arrays.toString(joinPoint.getArgs()));
    try {
      Object result = joinPoint.proceed();
      LOGGER.info("The method " + joinPoint.getSignature().getName() + "() ends with " + result);
      return result;
    } catch (IllegalArgumentException e) {
      LOGGER.error(
          "Illegal argument "
              + Arrays.toString(joinPoint.getArgs())
              + " in "
              + joinPoint.getSignature().getName()
              + "()");
      throw e;
    }
  }

  @Before("execution(* *.*(..)) && target(target) && args(a,b)")
  public void logParameter(Object target, double a, double b) {
    LOGGER.info("Target class : " + target.getClass().getName());
    LOGGER.info("Arguments : " + a + ", " + b);
  }
}
