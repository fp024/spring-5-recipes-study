## 레시피 2-24-i POJO끼리 애플리케이션 이벤트 주고받기

* 커스텀 ApplicationEvent를 만들어 발행 후, 이 이벤트를 받고 어떤 일을 하는 컴포넌트를 작성
* Cashier 가 checkout을 할 때, CheckoutEvent를 등록 (이벤트 송신)
  * 그러면 CheckoutListener 가 CheckoutEvent (이벤트 수신) 를 수신해서 로직 수행
