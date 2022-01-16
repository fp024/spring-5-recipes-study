## 레시피 10-08-i 트랜잭션 롤백 속성 설정하기

기본적으로 Unchecked 예외 일 때만 트랜젝션이 롤백됨 (RuntimeException 또는 Error)

직접 작성한 Checked 예외가 발생하더라도 롤백이 되야할 필요가 있을 때, rollback트랜젝션 속성에 정의함. 이렇게 설정을 하더라도 기본 규칙(Unchecked 예외는 롤백 / Checked예외는 롤백안함)은 준수함

JdbcBookShop 클래스의 purchase 메서드에서 일부러 IOExcpetion이 발생하게 해서 트랜젝션 롤백이 정상적으로 일어났는지 확인해보았다.

