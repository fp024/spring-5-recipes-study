## 레시피 11-06 재시도 - iii

#### *  AOP를 사용한 재시도 설정



### 이번 예제는...

> 11-06-ii 예제 기준으로 AOP 어노테이션 설정으로 바꾼 것 같은데...

### 진행

* ItemWriter 역할을 하는 클래스는 단순하게 서비스 호출하는 기능만 포함함. 
  * RetryTemplate 관련 코드 제거
* BatchConfiguration 에서 RetryTemplate 설정 했던 것 제거.. 그리고 `@EnableRetry` 붙임

* 로깅 확인

  ```
  11:34:00.275 [Test worker] INFO  org.fp024.study.spring5recipes.springbatch.config.DeadlockTestHelper - ### item: firstName_7
  11:34:03.276 [Test worker] INFO  org.fp024.study.spring5recipes.springbatch.config.DeadlockTestHelper - ### item: firstName_7
  11:34:09.279 [Test worker] INFO  org.fp024.study.spring5recipes.springbatch.config.DeadlockTestHelper - ### item: firstName_7
  ```

  예상대로 초기 3초 두번째 2배수 해서 6초 후 실행된 로그를 확인함.





#### csvFileReader(), lineMapper(), fieldSetMapper(), tokenizer() 를 줄이자..

앞에서 빌더로 코드를 줄일 수 있었던 것 같은데... 이번에 다시 바꿔두자..

* `FlatFileItemReaderBuilder<UserRegistrationDTO>` 로 메서드 하나로 줄였다.



그리고 이게 AOP를 사용하므로 사용하므로.. 아래 `aspectjweaver`디펜던시가 필요하다.

```groovy
runtimeOnly "org.aspectj:aspectjweaver:${aspectjVersion}"
```

