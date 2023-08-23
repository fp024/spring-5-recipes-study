## 레시피 2-20-iii AspectJ 애스펙트를 로드 타임 위빙하기

* 스프링 로드 타임 위버로 로드 타임에 위빙하기

* build.gradle

  ```groovy
  
  dependencies {
    ...
    implementation "org.springframework:spring-instrument:${springVersion}"
    ...
  }
  
  String instrumentLibPath(){
    return sourceSets.getByName("main").compileClasspath.find {
      cls -> return cls.getName().contains("spring-instrument")
    }
  }
  
  final INSTRUMENT_LIB_PATH = instrumentLibPath()
  
  test {
    jvmArgs "-javaagent:${INSTRUMENT_LIB_PATH}"
    ...
  }
  
  
  application {
    applicationDefaultJvmArgs = ["-javaagent:${INSTRUMENT_LIB_PATH}"] 
    ...
  }
  ```

  위와 같은식으로 해서 javaagent 지정을 하고 실행했을 때, 로그상으로는... 정상처럼 보이는데.

  ```
  16:04:19.791 [main] DEBUG org.springframework.context.weaving.DefaultContextLoadTimeWeaver - Found Spring's JVM agent for instrumentation
  16:04:19.802 [main] DEBUG org.springframework.context.weaving.DefaultContextLoadTimeWeaver - Found Spring's JVM agent for instrumentation
  (1 + 2i) + (2 + 3i) = (3 + 5i)
  (5 + 8i) - (2 + 3i) = (3 + 5i)
  16:04:19.918 [main] DEBUG org.springframework.context.annotation.AnnotationConfigApplicationContext - Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@1e81f160, started on Wed Jan 12 16:04:19 KST 2022
  16:04:19.919 [main] DEBUG org.springframework.context.weaving.DefaultContextLoadTimeWeaver - Removing all registered transformers for class loader: jdk.internal.loader.ClassLoaders$AppClassLoader
  ```

  Aspect 코드가 실행이 안된다 😥😥😥

TODO: 이 예제는 천천히 봐야겠다. 

---

IoC 컨테이너에 선언한 빈이 호출할 경우에만 스프링 에이전트가 어드바이스를 적용한다고 해서..

```java
  @RequiredArgsConstructor
  @Component("testBean")
  public static class TestBean {
    private final ComplexCalculator complexCalculator;

    public void test() {
      complexCalculator.add(new Complex(1, 2), new Complex(2, 3));
      complexCalculator.sub(new Complex(5, 8), new Complex(2, 3));
    }
  }
```

테스트 빈을 만들어서 확인해봤는데.. 여전히 안된다. 😂😂😂

