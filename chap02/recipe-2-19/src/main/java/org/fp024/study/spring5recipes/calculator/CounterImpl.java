package org.fp024.study.spring5recipes.calculator;

public class CounterImpl implements Counter {
  private int count;

  public void increase() {
    count++;
  }

  public int getCount() {
    return count;
  }
}
