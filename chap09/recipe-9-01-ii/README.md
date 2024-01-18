## 레시피 9-01-ii JDBC 템플릿으로 DB 수정하기

> 레시피 9-00-ii에서 insert 메서드에 JDBC Template 사용
>

### 이번 레시피에서 확인해야할  내용

* ✔ `PreparedStatementCreator` 를 활용해서 insert() 수정

* ✅  insert()의 JDBC Template 사용처를 람다식으로 변경




## 진행

##### 레시피 9-01-ii

* `PreparedStatementCreator` 를 활용해서 insert() 수정
* 나는 JDBC 템플릿은 Bean으로 선언했다..😅
* insert()외에도 update(), delete()를 변경함.



이전까지 테스트를 할 때.. 약간 아쉬운점이 있었는데,

DB초기화를 main환경에서 하기 때문에.. 

테스트 코드에서 DB초기화를 할 때. `@AfterEach` 에서 초기화를 해줬었는데..

이걸 Test 환경이라면 main의 DB 초기화를 하지 않도록 하는게 나아보였다.

그래서 main의 초기화 코드에는 `!test` 로 프로필을 설정해서 `test`프로필일 경우 빈을 생성하지 않도록하고..

```java
  /** test 프로필이 활성화 된상태에서는 DB 초기화를 하지 않는다. */
  @Profile("!test")
  @Bean
  DataSourceInitializer dataSourceInitializer() {
    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource());
    initializer.setDatabasePopulator(databasePopulator());
    return initializer;
  }
```



`build.grade`에는 test 프로필이 기본으로 설정되도록 했다.

```groovy
tasks.named('test') {
  systemProperty "spring.profiles.active", "test,${ACTIVE_PROFILES}"
}
```

이렇게 하면 또다른 문제가 생기는데

실제 실행과 같은 MainTests를 실행할 때는 또 초기화가 되야하기 때문에...

이걸 통합 테스트라 간주하고, 테스크를 추가했다.

```groovy
tasks.named('test') {
  useJUnitPlatform {
    excludeTags 'integration'
  }
  systemProperty "spring.profiles.active", "test,${ACTIVE_PROFILES}"
}

// integrationTest 테스크 추가
tasks.register('integrationTest', Test) {
  useJUnitPlatform {
    includeTags 'integration'
  }

  systemProperty "spring.profiles.active", "${ACTIVE_PROFILES}"

  testLogging {
    outputs.upToDateWhen { false }
    showStandardStreams = true
  }

  testClassesDirs = sourceSets.test.output.classesDirs
  classpath = sourceSets.test.runtimeClasspath
}
```

integration Tag가 붙은 테스트만 실행되도록 태그를 붙여주면 되었다.

```java
@Tag("integration")
class MainTests {
  @Test
  void testMain() {
    Main.main(null);
  }
}
```



여기까지 커맨드 상에서 다음과 같이 처리하면 쭉 실행이 되는데...

```bash
gradle clean test integrationTest
```

IntelliJ에서는 어떤 테스크를 실행할 것인지 물어보는데, VSCode에서는 이게 힘들다.

일단 이 예제에서는 이렇게두고.. 통합 테스트를 별도 경로로 분리하는게 낫겠다. 😅






## 의견

* ...



---

## 기타







## 정오표

* ...
  


---

## JavaDocs

* ...



---

### 문서 사용 아이콘

* ✅
* ✔
* ⬜
* ...

