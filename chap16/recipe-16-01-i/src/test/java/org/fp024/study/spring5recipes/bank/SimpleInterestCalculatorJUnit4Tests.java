package org.fp024.study.spring5recipes.bank;

import static org.junit.Assert.assertEquals;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

@Slf4j
public class SimpleInterestCalculatorJUnit4Tests {

  private InterestCalculator interestCalculator;

  @Before
  public void init() {
    interestCalculator = new SimpleInterestCalculator();
    interestCalculator.setRate(0.05);
  }

  @Test
  public void calculate() {
    LOGGER.info("### calculate() ###");
    double interest = interestCalculator.calculate(10000, 2);
    assertEquals(1000.0, interest, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void illegalCalculate() {
    LOGGER.info("### illegalCalculate()() ###");
    interestCalculator.calculate(-10000, 2);
  }
}
