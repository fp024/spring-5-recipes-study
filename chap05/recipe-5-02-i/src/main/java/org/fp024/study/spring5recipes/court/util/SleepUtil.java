package org.fp024.study.spring5recipes.court.util;

import java.util.Random;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SleepUtil {
  // Thread-Safe
  private static final Random RANDOM = new Random();

  public static void delay(long delay) {
    try {
      Thread.sleep(delay);
    } catch (InterruptedException e) {
      // Sonarlint 가이드대로 변경: (java:S2142)
      // InterruptedException를 받으면 다른 스레드가
      // 현재 sleep으로 일시 정지 상태의 스레드를 중단하겠다는 것인데..
      // 이때는 현재의 스레드를 정지시키는 것이 맞는 흐름 같다.
      // 이 블록에 아무 처리가 없으면 현재 스레드를 정지 시켜야할 상황에 정지 시킬 수 없는 상황이 생길 것 같다.
      LOGGER.warn("Interrupted!", e);
      Thread.currentThread().interrupt();
    }
  }

  public static void randomDelay() {
    // 이전에 실패 볼려고 5500으로 늘려놨던 것 같음 3초로 바꾸자! (AsyncSupportConfigurer의 timeout이 현재 5초로 설정되어있음)
    int delay = RANDOM.nextInt(3000);
    delay(delay);
  }
}
