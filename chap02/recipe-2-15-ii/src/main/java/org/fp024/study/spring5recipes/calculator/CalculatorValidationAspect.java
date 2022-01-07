package org.fp024.study.spring5recipes.calculator;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Order(0)
@Component
public class CalculatorValidationAspect {

  @Before("execution(* *.*(double, double))")
  public void validateBefore(JoinPoint joinPoint) {
    LOGGER.info(
        "validateBefore: {} / {}",
        joinPoint.getSignature().getName(),
        Arrays.toString(joinPoint.getArgs()));
    for (Object arg : joinPoint.getArgs()) {
      validate((Double) arg);
    }
  }

  private void validate(double a) {
    if (a < 0) {
      throw new IllegalArgumentException("Positive numbers only");
    }
  }
}
