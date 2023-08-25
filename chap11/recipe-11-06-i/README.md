## 레시피 11-06 재시도 - i 

* 데드락이 발생했을 때, 재시도를 3번까진 하는 설정을 추가하긴 했는데...

  아무래도 데드락 상황을 어떻게 만들지 모르겠다. 😅



#### 상황 재현을 하기 위해 ItemProcessor에다 넣어보기로 했음.

* `retryLimit(3)`
  * 해깔릴 수 있는 부분이긴한데.. 3번 실패를 허용한 다는 말이 아님.
  * 재시도를 3번 실행하니.. 2번까지 실패하고 3번째 재시도가 성공해야 Job이 성공으로 끝난다는 의미임.
* `retry(DeadlockLoserDataAccessException.class)`
  * 예외도 계층 구조가 적용되서 그냥 `Exception.class`라고 적으면 어느 예외든지라는 뜻이 됨.
* DeadlockTestProcessor 에서는...
  * `firstName_1` 인 입력이 들어왔고, 아직 예외 발생 한계 카운트를 넘지 않았다면 `DeadlockLoserDataAccessException` 예외를 발생시킴.



로그를 보니 예상대로 된다.

예외가 두번 생겨서 실패를 했지만.. 3번째에는 예외 없이 실행되서 잡이 성공적으로 끝났음.

```
15:01:06.265 [Test worker] DEBUG org.springframework.batch.core.step.item.SimpleRetryExceptionHandler - Handled non-fatal exception
org.springframework.dao.DeadlockLoserDataAccessException: count: 0; nested exception is java.lang.RuntimeException
...
15:01:06.286 [Test worker] DEBUG org.springframework.batch.core.step.item.SimpleRetryExceptionHandler - Handled non-fatal exception
org.springframework.dao.DeadlockLoserDataAccessException: count: 1; nested exception is java.lang.RuntimeException
...
...
15:01:06.574 [Test worker] DEBUG org.springframework.batch.core.step.AbstractStep - Step execution complete: StepExecution: id=0, version=23, name=User Registration CSV To DB Step, status=COMPLETED, exitStatus=COMPLETED, readCount=100, filterCount=0, writeCount=100 readSkipCount=0, writeSkipCount=0, processSkipCount=0, commitCount=21, rollbackCount=2
15:01:06.578 [Test worker] DEBUG org.springframework.batch.core.job.AbstractJob - Upgrading JobExecution status: StepExecution: id=0, version=23, name=User Registration CSV To DB Step, status=COMPLETED, exitStatus=COMPLETED, readCount=100, filterCount=0, writeCount=100 readSkipCount=0, writeSkipCount=0, processSkipCount=0, commitCount=21, rollbackCount=2, exitDescription=
15:01:06.578 [Test worker] DEBUG org.springframework.batch.core.job.AbstractJob - Job execution complete: JobExecution: id=0, version=1, startTime=Fri Aug 25 15:01:06 KST 2023, endTime=null, lastUpdated=Fri Aug 25 15:01:06 KST 2023, status=COMPLETED, exitStatus=exitCode=COMPLETED;exitDescription=, job=[JobInstance: id=0, version=0, Job=[User Registration Import Job]], jobParameters=[{date=1692943265804}]
15:01:06.585 [Test worker] INFO  org.springframework.batch.core.launch.support.SimpleJobLauncher - Job: [SimpleJob: [name=User Registration Import Job]] completed with the following parameters: [{date=1692943265804}] and the following status: [COMPLETED] in 419ms


```

* rollbackCount 가 2회로 발생했어도 재시도를 해서 100개 데이터를 모두 읽어 DB 저장했음.

---

