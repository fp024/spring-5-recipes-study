## 레시피 11-06 재시도 - i i



### 이번 예제는...

> 책에는 외부서비스에 대한 정의에 대해 그냥 인터페이스 하나만 있는데..
>
> 이 부분을  배치의 reader로 부터 로드된 내용을 DB에 저장하는 내용으로 구현하기로 했다.
>
> 1. 현재 배 레파지토리 DB와 서비스 DB (USER_REGISTRAION)를 하나로 쓰고 있었는데..  이 부분을 별도 DB로 분리
>
>    * 데이터 소스가 분리되야함.
>
>      ```java
>      public class BatchConfiguration {
>        ...
>        @Bean
>        BatchConfigurer configurer(@Qualifier("batchDataSource") DataSource dataSource) {
>          return new DefaultBatchConfigurer(dataSource);
>        }
>          
>        @Primary
>        @Bean(name = "batchDataSource", destroyMethod = "close")
>        HikariDataSource dataSource() {
>          HikariConfig hikariConfig = new HikariConfig();
>          hikariConfig.setDriverClassName(env.getProperty("batch.jdbc.driver"));
>          hikariConfig.setJdbcUrl(env.getProperty("batch.jdbc.url"));
>          hikariConfig.setUsername(env.getProperty("batch.jdbc.username"));
>          hikariConfig.setPassword(env.getProperty("batch.jdbc.password"));
>          return new HikariDataSource(hikariConfig);
>        }
>      ...
>      ```
>
>      * https://stackoverflow.com/questions/25540502/use-of-multiple-datasources-in-spring-batch
>      * BatchConfigurer를 재정의해서 어떤 데이터 소스를 사용할 것인지 명시적으로 설정해줘야함.
>      * batchDataSource 빈에 대해서 `@Primary` 설정을 해줘야함.
>      * DB프로퍼티 파일을 batch 레파지토리와 서비스 레파지토리로 2가지로 쓸 것이므로 파일이 따로 분리되어있더라도 프로퍼티 속성 키 이름이 중복되면 안됨.
>
> 2. outer 패키지 이하에 서비스 클래스를 모아두었음.
>
>    * 서비스 구성은 JPA로 구성
>
>    * 배치에서 처리하는 도메인은 DTO로 두고 그것을 ModelMapper로 복사해서 엔티티로 생성
>
>    * `@EnableJpaRepositories`에서 transactionManagerRef 속성은 배치에서 만든 트랜젝션 데이터 소스를 사용하지 않기 위해 별도 생성해서 기입
>
>      > 로그를 보면...
>      >
>      > ```
>      > 07:46:49.327 [Test worker] WARN  org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer - No transaction manager was provided, using a DataSourceTransactionManager
>      > ```
>      >
>      > 배치용으로는 따로 설정하지 않으면 배치 레파지토리용 데이터 소스 기준으로 DataSourceTransactionManager를 자동으로 만듬.



### 기타

약간 해깔리게 생각한 부분이 있음..

```java
  @Bean
  ExponentialBackOffPolicy backOffPolicy() {
    ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
    backOffPolicy.setInitialInterval(1000); // 첫 번째 시도 지연 값 1초
    backOffPolicy.setMultiplier(2); // 이후 시도할 때마다 재연이 얼마나 증가되는지 제어
    backOffPolicy.setMaxInterval(10000); // 지연 간격 10초
    // 최초는 1초만 기다리고, 두번째에는 10초, 새번째에는 증감값 고려해서 2배해서 20초 지연 감수 같음.
    return backOffPolicy;
  }
```

위 처럼 설정한 걸 마치 타임아웃 처럼 잘못생각했다.

예외가 터지고나서 어느정도 지연시간후 재시도를 결정하는 것임. 

어떤 Job 실행의 타임아웃이 아님..

```
10:54:08.151 [Test worker] INFO  org.fp024.study.spring5recipes.springbatch.config.DeadlockTestHelper - ### item: firstName_7
10:54:11.157 [Test worker] INFO  org.fp024.study.spring5recipes.springbatch.config.DeadlockTestHelper - ### item: firstName_7
10:54:17.166 [Test worker] INFO  org.fp024.study.spring5recipes.springbatch.config.DeadlockTestHelper - ### item: firstName_7
```

시간이 백오프 시간대로 늘어난게 보임.  

첫번째로그가 첫 실패고...

두번째로그가 `setInitialInterval(3000)`에 따라서 3초뒤에 수행됨.

세번째 로그는 `setMultiplier(2)` 에 따라서 6초뒤에 수행됨.





### 클래스 / 어노테이션 JavaDoc

### RetryTemplate

> 재시도 시맨틱을 사용하여 연산 실행을 단순화하는 템플릿 클래스입니다.
> 재시도 가능한 연산은 `RetryCallback` 인터페이스의 구현에 캡슐화되며 제공된 실행 메서드 중 하나를 사용하여 실행됩니다.
> 기본적으로 예외 또는 예외의 하위 클래스를 던지면 연산이 재시도됩니다. 이 동작은 `setRetryPolicy(RetryPolicy)` 메서드를 사용하여 변경할 수 있습니다.
>
> 또한 기본적으로 각 작업은 중간에 백오프 없이 최대 세 번까지 재시도됩니다. 이 동작은 `setRetryPolicy(RetryPolicy)` 및 `setBackOffPolicy(BackOffPolicy)` 속성을 사용하여 구성할 수 있습니다. 백오프 정책은 각 개별 재시도 시도 사이의 일시정지 시간을 제어합니다.
> 새 인스턴스는 빌더를 통해 유창하게 구성할 수 있습니다(예
>
> ```java
> RetryTemplate.builder()
>   .maxAttempts(10)
>   .fixedBackoff(1000)
>   .build();
> ```
>
> 더 많은 예제와 자세한 내용은 `RetryTemplateBuilder`를 참조하세요.
> 이 클래스는 스레드에 안전하며 작업을 실행할 때나 구성 변경을 수행할 때 동시 액세스에 적합합니다. 따라서 재시도 횟수와 사용되는 `BackOffPolicy`를 즉시 변경할 수 있으며 진행 중인 재시도 가능 작업에는 영향을 미치지 않습니다.

* *백오프* 연산: 구내 정보 통신망에서 데이터 전송 신호 간에 충돌이 발생되었을 때 이를 다시 전송할 때까지 걸리는 시간. (어휘 혼종어 정보·통신 )
* 재시도 횟수는 MaxAttemptsRetryPolicy를 설정해주면 별도 처리할 수 있는 것 같은데.. 아무 설정이 없으면 기본 3번 같다.

### ExponentialBackOffPolicy

> 주어진 설정에서 각 재시도 시도에 대한 백오프 기간을 한도까지 늘리는 `BackOffPloicy` 구현.
>
> 이 구현은 스레드에 안전하며 동시 액세스에 적합합니다. 
>
> 구성을 수정해도 이미 진행 중인 재시도 세트에는 영향을 미치지 않습니다. 
>
> `setInitialInterval(long)` 속성은 첫 번째 재시도에 대한 초기 지연 값을 제어하고 
>
> `setMultiplier(double)` 속성은 이후 시도할 때마다 지연이 얼마나 증가되는지를 제어합니다. 
>
> 지연 간격은 `setMaxInterval(long)`로 제한됩니다.

### @Primary

> 단일 값 종속성을 오토 와이어링할 자격이 있는 여러 후보가 있을 때 특정 빈에 우선순위를 부여해야 함을 나타냅니다. 후보 중 정확히 하나의 'primary' 빈이 존재하면 이 빈이 자동 와이어링된 값이 됩니다.
> 이 어노테이션은 Spring XML에서 `<bean>` 요소의 기본 속성과 의미적으로 동일합니다.
> 컴포넌트로 직접 또는 간접적으로 어노테이션된 모든 클래스 또는 `@Bean`으로 어노테이션된 메서드에 사용할 수 있습니다.
>
> Example
>
> ```java
> @Component
> public class FooService {
>   private FooRepository fooRepository;
>   
>   @Autowired
>   public FooService(FooRepository fooRepository) {
>     this.fooRepository = fooRepository;
>   }
> }
> 
> @Component
> public class JdbcFooRepository extends FooRepository {
>   public JdbcFooRepository(DataSource dataSource) {
>     // ...
>   }
> }
> 
> @Primary
> @Component
> public class HibernateFooRepository extends FooRepository {
>   public HibernateFooRepository(SessionFactory sessionFactory) {
>     // ...
>   }
> }
> ```
>
>  HibernateFooRepository는 `@Primary`로 표시되어 있기 때문에, 둘 다 동일한 Spring 애플리케이션 컨텍스트 내에 빈으로 존재한다고 가정하면 jdbc 기반 변형보다 우선적으로 주입되며, 이는 구성 요소 검색이 자유롭게 적용되는 경우에 자주 발생합니다.
> 컴포넌트 스캐닝이 사용되지 않는 한 클래스 수준에서 `@Primary`를 사용하는 것은 아무런 효과가 없습니다. XML을 통해 `@Primary` 어노테이션 클래스가 선언된 경우, @Primary 어노테이션 메타데이터는 무시되고 대신 `<bean primary="true|false"/>`가 우선됩니다.



### IntelliJ 특이사항

#### main() 메서드를 실행시켰을 때, main에서 예외를 밖으로 던지지 않아도 실행중에 RuntimeException이 발생하면 실패로 간주?

main()을 Junit에서 테스트로 돌리면 실패로 안나타나는데, 그냥 실행시켰을 경우 

`Caused by: java.lang.RuntimeException`가 발생했다고 실패로 간주함.?

DeadlockTestProcessor에서 RuntimeException을 포함해서 던지기는 하는데.. 그냥 코드를 수정했다.

```java
    if ("firstName_%s".formatted(TARGET_ID_NUMBER).equals(item.getFirstName())
        && count.get() < MAX_COUNT) {
      throw new DeadlockLoserDataAccessException(
          "count: %s".formatted(count.getAndIncrement()), null); // *
    }
```

cause 파라미터 값을 `new RuntimeException()`에서 null로 설정함...

그런데 이건 그냥 냅둬도 되었을 것 같긴하다..😅
