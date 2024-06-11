package org.fp024.study.spring5recipes.caching;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlainCalculationService implements CalculationService {
  @Override
  public BigDecimal heavyCalculation(BigDecimal base, int power) {
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      LOGGER.warn("Interrupted!", e);
      /* 중단하기 전에 처리해야 하는 모든 작업을 정리.  */
      Thread.currentThread().interrupt();
    }
    return base.pow(power);
  }
}
