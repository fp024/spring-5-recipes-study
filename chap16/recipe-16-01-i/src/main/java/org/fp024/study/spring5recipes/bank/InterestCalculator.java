package org.fp024.study.spring5recipes.bank;

public interface InterestCalculator {
  void setRate(double rate);

  double calculate(double amount, double year);
}
