package org.fp024.study.spring5recipes.executors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorsDemo {
  private static final DateTimeFormatter PATTERN =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public static void main(String[] args) throws Throwable {
    Runnable task = new DemonstrationRunnable();

    // 스레드 풀을 생성하고 가급적 이미 생성된 스레드를 사용하려고 시도합니다.
    ExecutorService cachedThreadPoolExecutorService = Executors.newCachedThreadPool();
    if (cachedThreadPoolExecutorService.submit(task).get() == null) {
      System.out.printf(
          "The cachedThreadPoolExecutorService " + "has succeeded at %s \n",
          LocalDateTime.now().format(PATTERN));
    }

    // 생성 스레드 개수를 제한하고 나머지 스레드는 큐잉합니다.
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);
    if (fixedThreadPool.submit(task).get() == null) {
      System.out.printf(
          "The fixedThreadPool has " + "succeeded at %s \n", LocalDateTime.now().format(PATTERN));
    }

    // 한번에 한 스레드만 사용합니다.
    ExecutorService singleThreadExecutorService = Executors.newSingleThreadExecutor();
    if (singleThreadExecutorService.submit(task).get() == null) {
      System.out.printf(
          "The singleThreadExecutorService " + "has succeeded at %s \n",
          LocalDateTime.now().format(PATTERN));
    }

    // 캐시된 스레드 풀을 사용합니다.
    ExecutorService es = Executors.newCachedThreadPool();
    if (es.submit(task, Boolean.TRUE).get().equals(Boolean.TRUE)) {
      System.out.println("Job has finished!");
    }

    // 타이머 기능을 모방합니다.
    ScheduledExecutorService scheduledThreadExecutorService = Executors.newScheduledThreadPool(10);
    if (scheduledThreadExecutorService.schedule(task, 30, TimeUnit.SECONDS).get() == null) {
      System.out.printf(
          "The scheduledThreadExecutorService " + "has succeeded at %s \n",
          LocalDateTime.now().format(PATTERN));
    }

    // 다음 문은 예외가 발생하거나 취소되지 않는 한 계속 실행됩니다.
    // scheduledThreadExecutorService.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
  }
}
