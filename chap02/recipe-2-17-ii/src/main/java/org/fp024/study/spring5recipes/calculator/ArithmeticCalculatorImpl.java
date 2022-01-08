package org.fp024.study.spring5recipes.calculator;

import org.springframework.stereotype.Component;

@LoggingRequired
@Component("arithmeticCalculator")
public class ArithmeticCalculatorImpl implements ArithmeticCalculator {
  @Override
  public double add(double a, double b) {
    double result = a + b;
    System.out.println(a + " + " + b + " = " + result);
    return result;
  }

  @Override
  public double sub(double a, double b) {
    double result = a - b;
    System.out.println(a + " - " + b + " = " + result);
    return result;
  }

  @Override
  public double mul(double a, double b) {
    double result = a * b;
    System.out.println(a + " * " + b + " = " + result);
    return result;
  }

  @Override
  public double div(double a, double b) {
    if (b == 0) {
      throw new IllegalArgumentException("Division by zero");
    }
    double result = a / b;
    System.out.println(a + " / " + b + " = " + result);
    return result;
  }
}
