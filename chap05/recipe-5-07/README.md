## 레시피 5-07 리액티브 REST 서비스로 JSON 발행/소비하기

> ...
> 

### 이번 레시피에서 확인해야할  내용

* 리액티브 REST 서비스로 JSON 발행/소비하기

  

## 진행

* 이번도 마찬가지로 main() 실행은

  ```sh
  # main() 실행
  gradle clean run
  ```

* Tomcat에 올려서 실행

  ```sh
  # 소스 경로로 실행
  gradle appRun
  # War로 패키징 하여 실행
  gradle appRunWar
  ```




### `public Flux<Reservation> listAll()` 호출

실시간으로 전송 받을 수 있는 것을 확인하기 위해 서비스의 findAll() 메서드에 지연을 추가

```java
 @Override
  public Flux<Reservation> findAll() {
    return Flux.fromStream(
        reservations.stream()
            .map(
                x -> {
                  try {
                    Thread.sleep(10); // 약간의 지연을 넣음.
                  } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                  }
                  return x;
                }));
  }
```



`LocalDate`를 Jackson으로 다룰 때, 설정이 필요한데..

```java
public class WebFluxConfiguration implements WebFluxConfigurer {
// ...
  @Bean
  ObjectMapper objectMapper() {
    return Jackson2ObjectMapperBuilder.json().modules(new JavaTimeModule()).build();
  }

  @Bean
  Jackson2JsonEncoder jackson2JsonEncoder() {
    return new Jackson2JsonEncoder(objectMapper());
  }

  @Bean
  Jackson2JsonDecoder jackson2JsonDecoder() {
    return new Jackson2JsonDecoder(objectMapper());
  }

  @Override
  public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
    configurer.defaultCodecs().jackson2JsonEncoder(jackson2JsonEncoder());
    configurer.defaultCodecs().jackson2JsonDecoder(jackson2JsonDecoder());
  }
// ...
}
```

위와 같이 설정. mvc에서 메시지 컨버터 재정의하는 것과 비슷하다.

HTML에서 리엑티브 REST API를 호출하는 HTML을 별도로 만들었는데...  다음과 같이 하였다.

* `GET /reservations`

  ```javascript
      fetch("/reservations", {
        method: 'GET',
        headers: {
          Accept: 'text/event-stream'
        }
      })
  ```

* `POST /reservations` 

  ```javascript
      fetch("/reservations", {
        method: 'POST',
        body: JSON.stringify({courtName: 'Tennis #1'}),
        headers: {
          "Content-Type": "application/json",
          "Accept": "text/event-stream"
        }
      })
  ```

이런 식으로 했을 때, 실시간으로 결과를 받을 수 있었다.

그런데 `Accept: 'text/event-stream'`를 요청헤더에 설정하지 않으면 서버 처리 완료후 한번에 받는 모습을 보였다. 

뭔가 스트리밍이 안되는 모습? 😅




## 의견

* 먼저 5.2에서 해놨던 fetch로 비동기 데이터 실시간으로 가져오는 방법 미리해둔게 도움이 많이 되었다. 👍




---

## 기타

#### 디펜던시 추가
```groovy
  // Jackson을 사용하니 다음 내용을 추가해주자! LocalDate 을 다루므로 jackson-datatype-jsr310도 필요.
  implementation "com.fasterxml.jackson.core:jackson-databind:${jacksonDatabindVersion}"
  implementation "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${jacksonVersion}"
```



### 리엑티브 프로그래밍에서 Mono와 Flux

- Mono와 Flux는 모두 Publisher 인터페이스의 구현체입니다. Publisher는 0개 이상의 데이터를 비동기적으로 발행하는 역할을 합니다.
- Mono는 0 또는 1개의 데이터만 발행할 수 있는 Publisher입니다. 즉, Mono는 성공적으로 완료되거나 에러로 실패하는 스트림을 표현할 때 유용합니다.
- Flux는 0개 이상의 데이터를 발행할 수 있는 Publisher입니다. 즉, Flux는 무한한 데이터를 비동기적으로 발행할 수 있는 스트림을 표현할 때 유용합니다.
- Mono와 Flux는 각각 Optional과 List와 비슷하다고 볼 수 있습니다. Optional은 0 또는 1개의 값만 가질 수 있고, List는 N개의 값들을 가질 수 있습니다.
- Mono와 Flux는 다양한 연산자들을 제공하여 데이터를 효과적으로 변환하고 조합할 수 있습니다. 예를 들어, map은 데이터에 1:1 변환 함수를 적용하고, flatMap은 데이터에 1:N 변환 함수를 적용합니다.



## 정오표

* ...

  


---

## JavaDocs

* ...
