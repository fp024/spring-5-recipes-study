package org.fp024.study.spring5recipes.calculator;

public class MinCalculatorImpl implements MinCalculator {

  @Override
  public double min(double a, double b) {
    double result = (a <= b) ? a : b;
    System.out.println("min(" + a + ", " + b + ") = " + result);
    return result;
  }
}
