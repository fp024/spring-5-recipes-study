## 레시피 16-10-i REST 클라이언트에 대한 통합 테스트 작성하기

> ...

### 이번 레시피에서 확인해야할  내용

* ✅ **16-10-i**:  RestTemplate 클라이언트 테스트

  



## 진행

RestGateWaySupport를 받아서 RestClient 를 수성했기 때문에 MockRestServiceServer를 사용할 수 있따.

처음에는 그냥 실제 API 테스트 하나? 했는데... Mock서버를 구성해서 정해진 요청을 보내면 정해진 응답을 하게한 것 구나..😅



## 의견

* MockRestServiceServer는 쓸 만해보인다. 수정할 수 있는 범위에 대해서는 WireMock 같은 것을 안써도 되는 것 같기도함.
* 16장을 다보았다. 괜찮은 내용였다. 👍👍




---

## 기타

### 응답 본문이 JSON으로 오게되므로, JSON 라이브러리를 추가해둬야 정상 동작한다.

```groovy
implementation (libs.jackson.databind)
```

나는 Jackson 추가했다.





## 정오표

* ...
  


---

## JavaDocs

* ...



---

### 문서 사용 아이콘

* ✅: Current Done
* ✔: Done
* ⬜: TODO
* ✖️: Skip
* ...

