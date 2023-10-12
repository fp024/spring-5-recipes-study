## 레시피 5-09 리액티브 핸들러 함수 작성하기

> ...
> 

### 이번 레시피에서 확인해야할  내용

* 리액티브 핸들러 함수 작성하기 

  

## 진행

* 5-07 예제 기준으로 ReservationRestController 수정

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



### 적용을 해보니.. 실시간 스트리밍 전송이 안되는 것 같음.

* 반환이 `Mono<ServerResponse>` 여서 그런지... body에 넘길 내용을 다 만들고 응답하는 것 같음. (관계없음.😅)

  * http://localhost:8080/reactiveJsonTest01,  http://localhost:8080/reactiveJsonTest02 의 동작이 실시간으로 전달되는게 보여지지 않음. 😅

  * ~~`Flux<T>`로 반환해야 실시간 응답을 받을 텐데...~~

    * 아니다. 그냥 Content-Type만 설정해주면 된다.

      ```java
       public Mono<ServerResponse> listAll(ServerRequest request) {
          return ServerResponse.ok()
              .contentType(getAccept(request)) // 컨텐트 타입을 요청 헤더의 내용을 보고 설정
              .body(reservationService.findAll(), Reservation.class);
        }
      
        private MediaType getAccept(ServerRequest request) {
          List<MediaType> acceptList = request.headers().accept();
          return acceptList.contains(MediaType.APPLICATION_NDJSON)
              ? MediaType.APPLICATION_NDJSON
              : MediaType.APPLICATION_JSON;
        }
      ```

      * 위 처럼 컨텐트 타입을 NDJSON으로 설정으로 설정하면 실시간으로 응답 받는 모습이 보임.
      * `@RestController`, `@GetMapping`등을 같이 쓰는 컨트롤러 메서드라면 Accpet-Header를 자동처리해줄 텐데.. 여기선 수동 처리해줘야함.






## 의견

* 드디어 5장을 다보았다. 👍 그래도 예제를 다 돌려봤다..  후반부에는 테스트 코드를 거의 안만들었는데, 이부분은 좀더 이해가 된 뒤에 만들어야겠다. 😅




---

## 기타

- ...



## 정오표

* ...

  


---

## JavaDocs

* ...
