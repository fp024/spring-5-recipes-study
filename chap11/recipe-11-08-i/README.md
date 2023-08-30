## 레시피 11-08-i 잡 실행하기 - i,ii,iii 내용 포함



### 이번 예제는...

> 이번에도 예제를 단순하게 만들었다.
>
> 내가 새로 수정한 예제가 11-08-i  ~ 11-08-iii를 커버한다.
>
> * 11-08-i 
>   * Main에서 여러가지 값 확인 한 내용, 종료 상태 확인 내용
> * 11-08-ii
>   * SimpleAsyncTaskExecutor 사용한 내용
> * 11-08-iii
>   * BatchConfigurer 재정의 한 내용



직전 예제 11-07-iv 기준으로 작성했는데...

저자님의 코드가 잘못된 점이 있다.


* 저자님 코드
  
  * https://github.com/Apress/spring-5-recipes/blob/5fc7cc9f3e97b2486541caa1ae8bf5a95c03152b/spring-recipes-4th/ch11/recipe_11_8_iI/src/main/java/com/apress/springrecipes/springbatch/Main.java#L20
  
  ```java
    JobExecution jobExecution = jobLauncher.run(job, jobParameters);
    BatchStatus batchStatus = jobExecution.getStatus();
  
    while (batchStatus.isRunning()) {
	      System.out.println("Still running...");
	      Thread.sleep(1000);
	  }
	```
	
	위와 같은 식으로 되어있는데... 
	
	위와 같은 식이면 거의 최초 JOB을 실행 시킨 시점에 상태값을 다른 변수에다 옮겨둔 셈이여서 항상 STARTING 상태가 된다.
	
	
	
	결국은 while 검사할 때... jobExecution 에서 항상 BatchStatus을 얻어오는 식으로 작성해야 비동기 실행되는 모습을 잘 확인할 수 있다.
	
	```java
	  while (jobExecution.getStatus().isRunning()) {
	      System.out.println("Still running...");
	      Thread.sleep(1000);
	  }
	```
	
	

