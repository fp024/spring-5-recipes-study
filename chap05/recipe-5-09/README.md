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

* 반환이 `Mono<ServerResponse>` 여서 그런지... body에 넘길 내용을 다 만들고 응답하는 것 같음.
  * http://localhost:8080/reactiveJsonTest01,  http://localhost:8080/reactiveJsonTest02 의 동작이 실시간으로 전달되는게 보여지지 않음. 😅
  * `Flux<T>`로 반환해야 실시간 응답을 받을 텐데...








## 의견

* ...




---

## 기타

- ...



## 정오표

* ...

  


---

## JavaDocs

* ...
