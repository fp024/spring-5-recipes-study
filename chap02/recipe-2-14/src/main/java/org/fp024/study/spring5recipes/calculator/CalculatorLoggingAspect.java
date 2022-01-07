package org.fp024.study.spring5recipes.calculator;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class CalculatorLoggingAspect {

  @Before("execution(* *.*(..))")
  public void logJoinPoint(JoinPoint joinPoint) {
    LOGGER.info("Join point kind : {}", joinPoint.getKind());
    LOGGER.info("Signature declaring type : {}", joinPoint.getSignature().getDeclaringTypeName());
    LOGGER.info("Signature name : {}", joinPoint.getSignature().getName());
    LOGGER.info("Arguments : {}", Arrays.toString(joinPoint.getArgs()));
    LOGGER.info("Target class : {}", joinPoint.getTarget().getClass().getName());
    LOGGER.info("This class : {}", joinPoint.getThis().getClass().getName());
  }
}
