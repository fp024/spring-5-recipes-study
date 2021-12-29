package org.fp024.study.spring5recipes.sequence;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;

class MainTest {
  @Test
  void testMain() {
    assertThrows(
        BeanCreationException.class,
        () -> {
          Main.main(null);
        },
        "@Required에 지정한 suffix필드에 값 설정을 하지 않아 예외");
  }
}
