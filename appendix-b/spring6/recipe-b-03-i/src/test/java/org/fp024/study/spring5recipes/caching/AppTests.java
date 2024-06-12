package org.fp024.study.spring5recipes.caching;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Slf4j
@SpringJUnitConfig(App.class)
class AppTests {

  @Autowired private App app;

  @Timeout(value = 600, unit = TimeUnit.MILLISECONDS)
  @Test
  void testMain() {
    try {
      app.run();
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
