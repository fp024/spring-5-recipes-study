## 레시피 11-08-iv 잡 실행하기 - 스케쥴러로 실행



### 이번 예제는...

> 테스크 스케쥴러로 Job을 실행하는 예제



### 진행

* 스케줄러가 3초마다 `runRegistrationJob`을 실행함.

* 아무런 추가 조치를 하지 않으면 무한 반복되는데...

  * 3번 실행했으면 스케쥴러를 끔.

    * 끌때...  테스트가 완료되길 기다리는 설정을 추가함.

      ```java
          // shutdown 전에 테스크가 완전히 완료되길 기다림
          // 아래 내용을 설정하지 않은 상태에서 Task내의 Thread.sleep() 부분이 걸리면 예외가 생기는 것 같다.
          taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
      ```

* Main함수에서 명시적으로 Application Context를 닫으면 안됨. 프로그램이 꺼져버림 😆
