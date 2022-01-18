## 레시피 11-01-ii 스프링 배치 기초 공사하기

* spring-batch 를 설정하는 예제 프로젝트

* `@EnableBatchProcessing`을 구성 클래스에 붙여서 설정 단순화

* 11-01-i 와 비교해서 아래 빈들에 대한 설정을 안해줘도 되었다.

  * PlatformTransactionManager transactionManager()
  * JobRepositoryFactoryBean jobRepository()
  * JobLauncher jobLauncher()
  * JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor()
  * JobRegistry jobRegistry()

  
