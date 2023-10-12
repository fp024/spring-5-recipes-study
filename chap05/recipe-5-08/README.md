## 레시피 5-08 비동기 웹 클라이언트

> ...
> 

### 이번 레시피에서 확인해야할  내용

* 클라이언트로서 API 호출

  

## 진행

💡 실행 전에 5-07 예제를 실행시켜놔야한다.

* 이번은 단순 main() 실행임.

  ```sh
  # main() 실행
  gradle clean run
  
  # 아무키 입력 받기 전까지 대기 ...
  ```

* Main코드에 System.in.read()로 키입력을 기다리는 부분이 있기 때문에 build.gradle에 다음 설정이 추가되야한다.

  ```groovy
  run {
    standardInput = System.in
  }
  ```

* WebClient 사용방식에서..

  ```java
      WebClient.create(url)
          .get()
          .uri("/reservations")
          .accept(MediaType.APPLICATION_NDJSON)
          .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Reservation.class))
          .subscribe(System.out::println);
  ```

  * accept 관련
    * `APPLICATION_STREAM_JSON`는 Depreacted되어 `APPLICATION_NDJSON`으로 써봤음.
  * exchage() 대신 exchangeToFlux() 또는 exchangeToMono()를 사용하라고 하는데, 0개 이상의 데이터이므로 exchangeToFlux()를 사용했음.
  * bodyToFlux에다가 String.class 대신 Reservation.class 을 넣었을 때, 모델 매핑도 잘 된다.

  



## 의견

* 이번장은 쉽게 끝났다.
  * 원래 테스트코드로서 이 내용을 5-07에다가 붙일려고 했는데... 테스트 코드를 어떤식으로 만들어야할지? 아직 잘몰라서.. API 호출 예제를 그냥 만들게 되었음...😅



---

## 기타

* ...



## 정오표

* ...

  


---

## JavaDocs

* ...
