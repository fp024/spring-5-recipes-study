package org.fp024.study.spring5recipes.calculator;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
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

  @AfterReturning(pointcut = "execution(* *.*(..))", returning = "result")
  public void logAfterReturning(JoinPoint joinPoint, Object result) {
    LOGGER.info("The method {}() ends with {}", joinPoint.getSignature().getName(), result);
  }

  @AfterThrowing(pointcut = "execution(* *.*(..))", throwing = "e")
  public void logAfterThrowing(JoinPoint joinPoint, IllegalArgumentException e) {
    LOGGER.error(
        "Illegal argument {} in {}()",
        Arrays.toString(joinPoint.getArgs()),
        joinPoint.getSignature().getName());
  }
}
