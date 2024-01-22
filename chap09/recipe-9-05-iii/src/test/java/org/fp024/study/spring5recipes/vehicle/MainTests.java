package org.fp024.study.spring5recipes.vehicle;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.fp024.study.spring5recipes.vehicle.exception.MyDuplicateKeyException;
import org.junit.jupiter.api.Test;

class MainTests {
  @Test
  void testMain() {
    assertThatThrownBy(() -> Main.main(null)) //
        .isInstanceOf(MyDuplicateKeyException.class);
  }
}
