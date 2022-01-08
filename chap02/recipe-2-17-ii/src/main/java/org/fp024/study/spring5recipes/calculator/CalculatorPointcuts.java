package org.fp024.study.spring5recipes.calculator;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CalculatorPointcuts {

  @Pointcut("within(@LoggingRequired *)")
  public void loggingOperation() {}
}
