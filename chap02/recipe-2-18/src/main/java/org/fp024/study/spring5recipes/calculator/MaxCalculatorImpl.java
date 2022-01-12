package org.fp024.study.spring5recipes.calculator;

public class MaxCalculatorImpl implements MaxCalculator {
  @Override
  public double max(double a, double b) {
    double result = (a >= b) ? a : b;
    System.out.println("max(" + a + ", " + b + ") = " + result);
    return result;
  }
}
