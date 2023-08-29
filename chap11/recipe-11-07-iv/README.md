## 레시피 11-07-iv 스텝 실행 제어하기 - 결정에 따른 조건부 스텝



### 이번 예제는...

> 이번에도 예제를 단순하게 만들었다.



### 결정 조건 변경...

기본 조건이 너무 발생하기 힘들어서 조건을 바꿨다.

```java
  private boolean isMercuryIsInRetrograde() {
    return Math.random() > .9;
  }  

  ...
  // 랜덤 Boolean 으로 바꿈.
  private final Random random = new Random();

  private boolean isMercuryIsInRetrograde() {
    return random.nextBoolean();
  }
```





* Decider가 MERCURY_IN_RETROGRADE를 반환할 경우

  ```
  08:32:02.236 [main] INFO  org.fp024.study.spring5recipes.springbatch.config.JobConfig - ### ✨Step 1 ###
  ...
  08:32:02.255 [main] INFO  org.fp024.study.spring5recipes.springbatch.config.HoroscopeDecider - ### 🎈 decide ###
  08:32:02.255 [main] INFO  org.fp024.study.spring5recipes.springbatch.config.HoroscopeDecider - ### 🎈 MERCURY_IN_RETROGRADE ###
  ...
  08:32:02.265 [main] INFO  org.fp024.study.spring5recipes.springbatch.config.JobConfig - ### ✨Step 2 ###
  ```

* COMPLETED 로 완료될 경우

  ```
  08:33:52.341 [main] INFO  org.fp024.study.spring5recipes.springbatch.config.JobConfig - ### ✨Step 1 ###
  ...
  08:33:52.350 [main] INFO  org.fp024.study.spring5recipes.springbatch.config.HoroscopeDecider - ### 🎈 decide ###
  ...
  08:33:52.360 [main] INFO  org.fp024.study.spring5recipes.springbatch.config.JobConfig - ### ✨Step 3 ###
  ```

  
