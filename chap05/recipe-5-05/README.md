## 레시피 5-05 스프링 웹플럭스로 리액티브 애플리케이션 개발하기

> ...
> 

### 이번 레시피에서 확인해야할  내용

* 먼저 했던 코드 예약 시스템의 기능을 리액티브 애플리케이션으로 전환

  

## 진행

### 추가 디펜던시

```groovy
  implementation "org.springframework:spring-webflux:${springVersion}"
  
  // 버전은 Spring Boot 2.7.x의 디펜던시 POM과 starter를 참고해서 Spring 5 기준으로 버전을 맞춤
  implementation "io.projectreactor:reactor-core:${reactorCoreVersion}"
  implementation "io.projectreactor.netty:reactor-netty:${reactorNettyVersion}"
  
  // View를 JSP 대신 Thymeleaf로 사용함.
  implementation "org.thymeleaf:thymeleaf-spring5:${thymeleafSpring5Version}"
  implementation "org.thymeleaf.extras:thymeleaf-extras-java8time:${thymeleafExtrasJava8timeVersion}"
```



### HttpServer를 생성하는 방법이 바뀜

reactor-core 버전이 올라가서 그런지, 책 내용대로 실행할 수가 없다. 신버전 메서드에 맞춰서 바꿔봄.

```java
// reactor-core 버전: 3.1.3.RELEASE
// reactor-netty 버전: 0.7.3.RELEASE 
HttpServer.create("localhost", 8090)
        .newHandler(adapter).block();

// =======================================

// reactor-core 버전: 3.4.32
// reactor-netty 버전: 1.0.36
// 다음과 같이 바꿈. ==> 
    DisposableServer server =
        HttpServer.create() //
            .host("localhost")
            .port(8080)
            .handle(adapter)
            .bindNow();
    server.onDispose().block();
  // 이렇게 해야지만 애플리케이션이 끝나지 않고 서버가 대기함.
```



### 서버 실행

서버 실행할 때 main() 메서드만 실행시키면 되므로 application 플러그인 을 추가하고

```groovy
plugins {
  id 'application'
  ...
}
application {
  mainClass = 'org.fp024.study.spring5recipes.reactive.court.ReactorNettyBootstrap'
}
```

다음 명령을 실행해서 서버를 실행한다.

```sh
gradle clean run
```





## 의견

일단 예제를 다 따라서 동작확인을 해보긴 했음.. 😅

그다지 감이 안오는게 딱히 리엑티브 방식으로 만들 필요가 없는 예제라서 그런건가? 

Greety 설정은 처음에 뺐다가... 저자님께서 서블릿 컨테이너에도 반영해보라고 하시길레... 다시 넣어두었다.

```sh
# 컴파일 경로로 실행 : https://akhikhl.github.io/gretty-doc/appRun-task.html
gradle clean appRun
# War 파일을 생성하여 실행: https://akhikhl.github.io/gretty-doc/appRunWar-task.html
gradle clean appRunWar
```

실행은 잘됨.. 그런데 서버 셧다운이 부드럽게 안되는 듯..😅



레시피 마지막 부분에 저자님께서 도커에다도 반영 해보라고 하셨는데, 지금 잘 모르겠다. 아무래도 buildDocker 테스크 실행이 그냥 되는게 아닌 것 같은데...

이부분은 나중에 해보자..

---

## 기타

#### Netty에서 실행되다 보니 Webjars의 기본 경로로 사용할 수가 없었다.

결국 별도 컨트롤러를 만들어서 `WebJarAssetLocator`를 통해 리소스를 반환하도록 설정했다.

* [WebjarsController](src/main/java/org/fp024/study/spring5recipes/reactive/court/common/WebjarsController.java)



#### WebFluxConfigurer 설정에서는 다음과 같은 설정을 못하는 것 같다.

```java
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("/", "/index.html");
  }
```

단순한 인덱스 컨트롤러를 그냥 만듬.😅

```java
@Controller
public class IndexController {
  @GetMapping("/")
  public String index() {
    return "redirect:index.html";
  }
}
```



#### 프로젝트에서 불필요한 부분 제거 내용

* 아직은 폼 검증을 하진 않아서, Hibernate Validator 디펜던시는 제외 해둠. 다음 예제 부터 진행하겠다.

  ```groovy
    implementation "org.hibernate.validator:hibernate-validator:${hibernateValidatorVersion}"
    testRuntimeOnly "org.glassfish:javax.el:${javaxElVersion}"
  ```

* `@NonNull`을 사용하지 않으면 jsr305 을 포함할 필요는 없어서 제외

  ```groovy
  implementation "com.google.code.findbugs:jsr305:${findBugsJsr305Version}"
  ```

  





## 정오표

* ...

  


---

## JavaDocs

* ...
