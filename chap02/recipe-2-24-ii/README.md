## 레시피 2-24-ii POJO끼리 애플리케이션 이벤트 주고받기

* 2-24-ii 와 비교해서 CheckoutEvent 가 일반 POJO가 되었고,  CheckoutListener 도 별도 인터페이스 구현 없이 애플리케이션 이벤트를 받는 메서드 위에 `@EventListener`만 붙여줬다.
