package org.fp024.study.spring5recipes.bank;

import static org.testng.Assert.assertEquals;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Slf4j
public class SimpleInterestCalculatorTestNGTests {

  private InterestCalculator interestCalculator;

  @BeforeMethod
  public void init() {
    interestCalculator = new SimpleInterestCalculator();
    interestCalculator.setRate(0.05);
  }

  @Test
  public void calculate() {
    LOGGER.info("### calculate() ###");
    double interest = interestCalculator.calculate(10000, 2);
    assertEquals(interest, 1000.0, 0);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void illegalCalculate() {
    LOGGER.info("### illegalCalculate() ###");
    interestCalculator.calculate(-10000, 2);
  }
}
