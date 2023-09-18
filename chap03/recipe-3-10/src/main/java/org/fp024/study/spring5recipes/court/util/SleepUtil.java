package org.fp024.study.spring5recipes.court.util;

import java.util.Random;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SleepUtil {
  // Thread-Safe
  private static final Random RANDOM = new Random();

  public static void sleepMax(long ms) {
    try {
      Thread.sleep(RANDOM.nextLong(500 + 1));
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }
  }
}
