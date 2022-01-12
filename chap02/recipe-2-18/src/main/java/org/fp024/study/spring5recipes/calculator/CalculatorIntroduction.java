package org.fp024.study.spring5recipes.calculator;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CalculatorIntroduction {
  @DeclareParents(
      value = "org.fp024.study.spring5recipes.calculator.ArithmeticCalculatorImpl",
      defaultImpl = MaxCalculatorImpl.class)
  public MaxCalculator maxCalculator;

  @DeclareParents(
      value = "org.fp024.study.spring5recipes.calculator.ArithmeticCalculatorImpl",
      defaultImpl = MinCalculatorImpl.class)
  public MinCalculator minCalculator;
}
