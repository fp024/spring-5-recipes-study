package org.fp024.study.spring5recipes.caching;

import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class MainTests {
  @Test
  void testMain() {
    await()
        .atMost(Duration.ofMillis(1500))
        .until(
            () -> {
              Main.main(null);
              return true;
            });
  }

  @Test
  @Timeout(unit = TimeUnit.MILLISECONDS, value = 1500)
  void testMain_02() {
    Main.main(null);
  }
}
