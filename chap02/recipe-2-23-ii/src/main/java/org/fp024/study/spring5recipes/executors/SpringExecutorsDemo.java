package org.fp024.study.spring5recipes.executors;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class SpringExecutorsDemo {
  // 각 작업에 대해 새 스레드를 시작하여 비동기적으로 실행하는 TaskExecutor 구현입니다.
  // "concurrencyLimit" 빈 속성을 통해 동시 스레드 제한을 지원합니다.
  // 기본적으로 동시 스레드 수는 무제한입니다.
  @Autowired private SimpleAsyncTaskExecutor asyncTaskExecutor;

  // 호출 스레드에서 동기적으로 각 작업을 실행하는 TaskExecutor 구현입니다.
  // 주로 테스트 시나리오용입니다.
  // 호출 스레드에서의 실행은 스레드 컨텍스트(예: 스레드 컨텍스트 클래스 로더 또는 스레드의 현재 트랜잭션 연결)에 참여하는 이점이 있습니다.
  // 즉, 많은 경우에 비동기 실행이 선호될 것입니다. 이러한 시나리오에는 대신 비동기 TaskExecutor를 선택하십시오.
  @Autowired private SyncTaskExecutor syncTaskExecutor;

  // JDK java.util.concurrent.Executor를 사용하고
  // 이를 위한 Spring org.springframework.core.task.TaskExecutor를 노출하는 어댑터.
  // 또한 확장된 java.util.concurrent.ExecutorService를 감지하여
  // 그에 따라 org.springframework.core.task.AsyncTaskExecutor 인터페이스를 조정합니다.
  @Autowired private TaskExecutorAdapter taskExecutorAdapter;

  //
  @Autowired private ThreadPoolTaskExecutor threadPoolTaskExecutor;

  // 실행할 작업
  @Autowired private DemonstrationRunnable task;

  @PostConstruct
  public void submitJobs() {
    syncTaskExecutor.execute(task);
    taskExecutorAdapter.submit(task);
    asyncTaskExecutor.submit(task);

    for (int i = 0; i < 500; i++) {
      threadPoolTaskExecutor.submit(task);
    }
  }

  public static void main(String[] args) {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(ExecutorsConfiguration.class)) {
      // JVM 런타임에 SpringContextShutdownHook이라는 종료 후크를 등록하고
      //  해당 시점에 이미 닫혀 있지 않은 경우 JVM 종료 시 이 컨텍스트를 닫습니다.
      // 실제 닫기 절차를 위해 doClose()에 위임합니다.
      context.registerShutdownHook();
    }
  }
}
