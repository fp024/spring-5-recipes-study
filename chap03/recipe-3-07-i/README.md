## 레시피 3-7-i 뷰와 콘텐트 협상 활용하기



### 이번 레시피에서 확인해야할  내용

URL에 확장자 없이 accept 헤더로 올바른 확장자의 리소스 받기

* ViewResolverConfiguration
  * configureContentNegotiation()
  * contentNegotiatingViewResolver()
* ReservationQueryControllerTest
  * testReservationSummary()



## 진행

* 이번 예제는 확실히 문제가 있음. 🎃

* 책에 설명하기위한 최소한의 코드만 나열하고 실제 동작이 검증되지않음
  * /reservationSummary 경로에 대한 주제를 다루는데, 그것에 대한 진입점이 없고.. 단지 view설정만 있음.

* 이 예제도 천천히 생각해야겠다...



## 의견

* 원 저자님께선 제발!!! 예제의 최소한의 동작을 검증하고 공유했으면 좋겠습니다. 
  * 설명이 들어갔으면 테스트코드는 없더라도 최소한 실행해서 책의 내용의 동작을 확인할 수 있는 코드는 다 들어가야되지 않을까요?
  * 읽는 사람이 어느정도 코드 추가해서 테스트 해보는 것에 대해서는 이해할 수 있지만, 이 책은 그 수준이 너무 심한 것 같습니다.
  * Spring Security in Action 책의 예제코드를 한번 봐보시길 바랍니다. 그 저자님은 어떤식으로 했는지?
    * 그 책의 경우는, 책의 내용과 예제가 일치하고, 테스트 코드가 잘 되어있기 때문에, Spring Security 버전이 많이 올라가더라도 책을 읽는 사람이 대응을 할 수 있어요.
