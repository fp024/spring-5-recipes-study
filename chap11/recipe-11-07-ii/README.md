## 레시피 11-07-ii 스텝 실행 제어하기 - 동시성



### 이번 예제는...

> 배치 코어와는 관계없는 실제 구현은 하지 않기로 했다.
>
> 구현을 하려고 힘을 쓰다보니... 배치 관련 코드가 명확하게 안보임.





### 동시 실행 테스트

```java
  @Bean
  Job nightlyRegistrationJob() {
    JobBuilder builder = jobs.get("nightlyRegistrationJob");

    Flow reportStatisticsFlow =
        new FlowBuilder<Flow>("reportStatisticsFlow").from(reportStatistics()).end();
    Flow resultToQueueFlow = //
        new FlowBuilder<Flow>("resultToQueueFlow").from(resultToQueue()).end();

    return builder //
        .start(loadRegistration())
        .split(new SimpleAsyncTaskExecutor())
        .add(reportStatisticsFlow, resultToQueueFlow)
        .end()
        .build();
  }
```

책의 코드대로 해서는 안되었다.

`add()` 메서드 부분에서 타입이 안맞아서 에러남.. 

위에 한 것 처럼 Flow를 만들어서 add()에 넣어줘야함.



### 실행 확인

```
20:10:23.930 [SimpleAsyncTaskExecutor-2] INFO  org.fp024.study.spring5recipes.springbatch.config.UserJob - ### DB로 부터 회원 데이터 읽어서, 메시지 큐 전달 ###
20:10:23.930 [SimpleAsyncTaskExecutor-1] INFO  org.fp024.study.spring5recipes.springbatch.config.UserJob - ### DB로 부터 회원 데이터 읽어서, 통계 생성 ###
```

로그 상으로 거의 동시에 실행된 것 을 알수 있었다.
