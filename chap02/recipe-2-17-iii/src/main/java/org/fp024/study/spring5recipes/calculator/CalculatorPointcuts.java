package org.fp024.study.spring5recipes.calculator;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CalculatorPointcuts {

  @Pointcut("within(ArithmeticCalculator+)")
  public void arithmeticOperation() {}

  @Pointcut("within(UnitCalculator+)")
  public void unitOperation() {}

  @Pointcut("arithmeticOperation() || unitOperation()")
  public void loggingOperation() {}
}
