## 레시피 b-06-i 트랜잭션이 걸린 리소스의 캐시 동기화하기 - Spring 6.x + Ehcache 3.x 전환

> ...

### 이번 레시피에서 확인해야할  내용

* ✅ **b-06-i**:  트랜젝션을 인지하는 캐시의 구현

  



## 진행

* 디펜던시 추가

  ```groovy
  implementation 'org.springframework:spring-jdbc'
  implementation (libs.h2)  // 💡EmbeddedDatabaseBuilder를 통해 사용할 때는 implementation이 되야한다.
  ```

* 실행

  ```
  >gradle clean run
  
  > Task :run
  00:57:35.358 [main] INFO  org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory - Starting embedded database: url='jdbc:h2:mem:customers;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false', username='sa'
  00:57:35.544 [main] DEBUG org.springframework.cache.ehcache.EhCacheManagerFactoryBean - Initializing EhCache CacheManager
  Get 'Unknown Customer' (result) : null
  Create new Customer (result) : Customer [id=1, name=Marten Deinum]
  Get 'New Customer 1' (result) : Customer [id=1, name=Marten Deinum]
  Get 'New Customer 2' (result) : Customer [id=1, name=Marten Deinum]
  Get 'Updated Customer 1' (result) : Customer [id=1, name=Josh Long]
  Get 'Updated Customer 2' (result) : Customer [id=1, name=Josh Long]
  Get 'Deleted Customer 1' (result) : null
  Get 'Deleted Customer 2' (result) : null
  
  StopWatch 'Cache Evict and Put': running time = 2098100700 ns
  ---------------------------------------------
  ns         %     Task name
  ---------------------------------------------
  553273500  026%  Get 'Unknown Customer'    // (1)
  014182100  001%  Create New Customer
  000429000  000%  Get 'New Customer 1'
  000134600  000%  Get 'New Customer 2'
  005164300  000%  Update Customer
  501675600  024%  Get 'Updated Customer 1'   // (2)
  000214900  000%  Get 'Updated Customer 2'
  001578100  000%  Remove Customer
  516663900  025%  Get 'Deleted Customer 1'   // (1)
  504784700  024%  Get 'Deleted Customer 2'   // (1)
  
  00:57:37.796 [main] DEBUG org.springframework.cache.ehcache.EhCacheManagerFactoryBean - Shutting down EhCache CacheManager
  00:57:37.803 [main] INFO  org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory - Shutting down embedded database: url='jdbc:h2:mem:customers;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false'
  
  BUILD SUCCESSFUL in 4s
  5 actionable tasks: 5 executed
  >
  ```

  이 예제는 지연 코드가 없었는데, 구별을 확실하게 하기 위해서 지연 코드를 넣었다.

  1. (1) 없는 유저 조회할 때는 항상 지연이 생김 (결과가 null 일 때 캐시하지 않음.)
  2. (2) Update 후 제거된 유저를 다시 불러올 때.. 지연 




## 의견

처음에... Statement.RETURN_GENERATED_KEYS가 누락 된 것 때문에, 테스트 코드를 만들어서 문제를 확인하려고 JdbcCustomerRepositoryTests를 만들었는데...

테스트 코드에 트랜젝션이 걸리게되면 뭔가 웃기게 된다. 😂

setTransactionAware(true)가 콜라보되면서 예상치 못한 동작이 생기는 것 같은데...

이 부분은 Test 메서드의 순서를 정해주는게 이해잘 될 것 같다.

```java
@SpringJUnitConfig(classes = CustomerConfiguration.class)
@DirtiesContext
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTests {
    // (0) DB 저장 및 캐시도 같이 생성됨.
  @BeforeAll
  void init() {
		// 초기 데이터 입력 - 테스트 클래스 시작시 1번 실행
  }

  // (1) 새로운 유저 생성, DB 저장 및 캐시도 같이 생성
  @Order(1)
  @Test
  void testCreate() { /* ... */ }

  // (2) 1번 유저 조회 - 캐시 조회
  @Order(2)
  @Test
  void testFind() { /* ... */ }

  // (3) 1번 유저 업데이트, 캐시 갱신
  @Order(3)
  @Test
  void testUpdate() { /* ... */ }

  // (4) 1번 유저 삭제, 캐시도 삭제
  @Order(4)
  @Test
  void testRemove() { /* ... */ }
}
```

테스트 메서드안에 캐시를 조작하는 메서드가 2개 이상 있을 때... 테스트 메서드에 `@Transactinal`을 정의하면 테스트가 끝나서 트랜젝션이 종료될 때까지 캐시 커밋이 안되서 해깔리게 되는 것 같다..

테스트 클래스에는 트랜젝션을 걸지 않고 확인을 하는게 나은 것 같기도하다..



### 💡 Spring 6.x + Ehcache 3.x 전환

recipe-b-05-ii-v 를 먼저 해야하는데... 이걸 먼저 전환했다. 😅

전환에 있어 중요한 부분은..

##### ehcache의 key-type도 기본값 Object로 두는게 나을 것 같다.

* 캐시 키도 여러가지가 될수도 있고... 관리를 스프링에게 맡기는게 나을 것 같음.

##### Spring 6.1 부터의 변경사항인 파라미터 인자가 원시타입일 때는 VM 옵션으로 -parameters 붙여줘야하는 부분.

```groovy
tasks.withType(JavaCompile) {
  options.compilerArgs << '-parameters'
}
```

* [Upgrading to Spring Framework 6.x · spring-projects/spring-framework Wiki · GitHub](https://github.com/spring-projects/spring-framework/wiki/Upgrading-to-Spring-Framework-6.x#upgrading-to-version-61)

  > LocalVariableTableParameterNameDiscoverer는 6.1에서 제거되었습니다. 결과적으로 Spring Framework 및 Spring 포트폴리오 프레임워크 내의 코드는 더 이상 바이트코드를 구문 분석하여 매개변수 이름을 추론하려고 시도하지 않습니다. 종속성 주입, 속성 바인딩, SpEL 표현식 또는 매개변수 이름에 의존하는 기타 사용 사례에 문제가 발생하는 경우 매개변수 이름 보존을 위해 일반적인 Java 8+ `-parameters` 플래그를 사용하여 Java 소스를 컴파일해야 합니다. -debug 컴파일러 플래그) StandardReflectionParameterNameDiscoverer와 호환되도록 합니다. Groovy 컴파일러는 동일한 목적으로 `-parameters` 플래그도 지원합니다. Kotlin 컴파일러에서는 `-java-parameters` 플래그를 사용하세요.

이 내용을 넣어주지 않으면 SpEL 식에서 전달 객체를 null로 판단하는 경우가 있었다.

```java
  @Override
  @CacheEvict(cacheNames = "customers", key = "#customer.id")
  public void update(Customer customer) {
    ...
```

update 메서드 성공 이후 캐시 취소 처리를 할 때... 분명이 customer가 null이 아니고 안의 id값도 있는 상태인데... 에러가 났었음.

```
17:44:43.823 [main] INFO  org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory - Shutting down embedded database: url='jdbc:h2:mem:customers;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false'
Exception in thread "main" org.springframework.expression.spel.SpelEvaluationException: EL1007E: Property or field 'id' cannot be found on null
```

`-parameters`  VM옵션을 붙이고 해결됨.






---

## 기타

### Another unnamed CacheManager already exists in the same VM. Please provide unique names for each CacheManager in the config or do one of following:

1. Use one of the CacheManager.create() static factory methods to reuse same CacheManager with same name or create one if necessary
2. Shutdown the earlier cacheManager before creating new one with same name.



테스트 클래스가 1개 모를 수 있는데, 여러개일 때...  위의 오류가 발생할 수 있는데,

* https://stackoverflow.com/questions/10013288/another-unnamed-cachemanager-already-exists-in-the-same-vm-ehcache-2-5
* 나는 캐시 메니저가 초기화되는 모든 테스트 클래스에 `@DirtiesContext`를 붙였다. 2번 권고 사항을 따른 것 같음.
* 예전에 Spring Security 장에서도 경험을 했다.
  * https://github.com/fp024/spring-5-recipes-study/tree/master/chap07/recipe-7-03-vi#junit%EC%97%90%EC%84%9C-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%88%98%ED%96%89%EC%8B%9C-ehcache%EA%B4%80%EB%A0%A8-%EC%98%88%EC%99%B8-%EB%B0%9C%EC%83%9D%ED%95%A0-%EB%95%8C



## 정오표

* p990: INSERT를 하면서 AUTO_INCREMENT 값을 받아와야하는데, 관련 설정이 되어있지 않다.

  ```java
  PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
  ```

  `Statement.RETURN_GENERATED_KEYS`를 지정해서 KeyHolder에서 키 값을 가져올 때, null이 되지 않도록 한다.

* schema.sql

  * name 컬럼 정의 끝에 `,`를 제거한다.





---

## JavaDocs

### AbstractTransactionSupportingCacheManager의 setTransactionAware()

> 이 CacheManager가 트랜잭션 인식 캐시 개체를 노출해야 하는지 여부를 설정합니다.
>
> 기본값은 "false"입니다. 캐시 넣기/제거 작업을 진행 중인 Spring 관리 트랜잭션과 동기화하고 성공적인 트랜잭션의 커밋 후 단계에서만 실제 캐시 넣기/제거 작업을 수행하려면 이를 "true"로 설정합니다.



---

### 문서 사용 아이콘

* ✅: Current Done
* ✔: Done
* ⬜: TODO
* ✖️: Skip
* ...

