package org.fp024.study.spring5recipes.caching;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(App.class)
class AppTests {

  @Autowired private App app;

  @Test
  void testMain() {
    app.run();
  }
}
