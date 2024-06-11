package org.fp024.study.spring5recipes.caching;

import java.math.BigDecimal;

public interface CalculationService {
  BigDecimal heavyCalculation(BigDecimal base, int power);
}
