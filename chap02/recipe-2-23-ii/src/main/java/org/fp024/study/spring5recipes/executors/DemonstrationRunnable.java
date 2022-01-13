package org.fp024.study.spring5recipes.executors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class DemonstrationRunnable implements Runnable {
  private static final DateTimeFormatter PATTERN =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @Override
  public void run() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println(Thread.currentThread().getName());
    System.out.printf("Hello at %s \n", LocalDateTime.now().format(PATTERN));
  }
}
