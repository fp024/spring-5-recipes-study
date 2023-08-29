## 레시피 11-07-iii 스텝 실행 제어하기 - 상태에 따른 조건부 스탭



### 이번 예제는...

> 이번 예제도 책 코드로는 테스트가 불가능하다. 책은 스프링 배치버전이 딱 4.0.0 이라서 그럴 수 있을 것 같긴하다.. 지금 내환경은 4.3.9... 그렇다고 버전 낮추긴 싫음..😂



### Step들을 @Bean으로 설정하는 이유

```java
  @Bean
  Job nightlyRegistrationJob() {
    return jobs //
        .get("nightlyRegistrationJob")
        .start(loadRegistration())
        .on("COMPLETED")
        .to(asyncFlow())
        .from(loadRegistration())
        .on("FAILED")
        .to(failedStep())
        .end()
        .build();
  }
  
  ...
  
    @Bean
  Step loadRegistration() {
    TaskletStep step =
        steps
            .get("User Registration CSV To DB Step")
            .tasklet(
                (contribution, chunkContext) -> {
                  LOGGER.info("### 🥲 CSV로 부터 데이터 읽어서 DB에 저장 중... 예외 발생  ###");

                  throw new IllegalStateException("임의 예외");
                })
            .build();
    LOGGER.info("### step hash: {} ", step.hashCode());
    return step;
  }
```

위 코드에서 start하고 from에서 스탭의 시작상태를 설정할 수 있는데..  사용하는 스탭이 빈이 아니면 단일 스탭을 가리키지 않게되서 다음 상태를 정의하지 않았다고 오류남... 🥲



만약 loadRegistration()에서 `@Bean`을 빼저리면 다음 오류가 발생함.

```
Caused by: org.springframework.batch.core.job.flow.FlowExecutionException: Next state not found in flow=nightlyRegistrationJob for state=nightlyRegistrationJob.step0 with exit status=FAILED
```

빙챗이 자꾸 `@Bean` 빼고 메서드 호출해도 된다고 우김 ㅠㅠ, 그렇게 하려면 한번 메서드를 호출하고 변수로 받아놓고 그걸 사용해야하는데...



### 기타

와일드 카드 설정이나 상태코드를 사용자 정의하는 건 넘어가자..
