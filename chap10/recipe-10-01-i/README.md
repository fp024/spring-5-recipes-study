## 레시피 10-01-i 트랜젝션 관리의 중요성

* 책 구매시 재고 소진 후, 사용자 잔액을 차감하는데, 차감부분에서 오류가 났을때, 재고소진 롤백 처리가 안되어있어, 재고는 그대로 빠져있다.
* 스프링 JDBC 지원 없이 생짜로 구현함.
* DriverManagerDataSource 대신 HikariCP 설정을 넣어봤다.

