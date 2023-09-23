package org.fp024.study.spring5recipes.court.util;

import java.util.Random;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SleepUtil {
  // Thread-Safe
  private static final Random RANDOM = new Random();

  public static void delay(long delay) {
    try {
      Thread.sleep(delay);
    } catch (InterruptedException e) {
    }
  }

  public static void randomDelay() {
    int delay = RANDOM.nextInt(5500);
    delay(delay);
  }
}
