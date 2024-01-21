package org.fp024.study.spring5recipes.vehicle;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;

class MainTests {
  @Test
  void testMain() {
    assertThatThrownBy(() -> Main.main(null)) //
        .isInstanceOf(DuplicateKeyException.class);
  }
}
