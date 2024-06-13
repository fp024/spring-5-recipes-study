package org.fp024.study.spring5recipes.caching;

import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/*
 이 테스트 메서드는 캐시된 부분만 체크하는 것이 아니고,
 메인 클래스 전체 실행시간을 체크하는 것이니 시간을 더 줘보자!
*/
class MainTests {
  @Test
  void testMain() {
    await()
        .atMost(Duration.ofMillis(3000))
        .until(
            () -> {
              Main.main(null);
              return true;
            });
  }

  @Test
  @Timeout(unit = TimeUnit.MILLISECONDS, value = 3000)
  void testMain_02() {
    Main.main(null);
  }
}
