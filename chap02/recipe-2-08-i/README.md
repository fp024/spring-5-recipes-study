## 레시피 2-8-i 어노테이션을 이용해 POJO 초기화/폐기 커스터마이징하기

* @Bean의 정의 부에서 initMethod, destoryMethod 속성을 설정하여, 초기화, 폐기 콜백 메서드로 동록하여 사용하는 예.
  * 빈 생성 이전에 initMethod 로 등록한 메서드 실행.
  * 빈 폐기 직전에 destoryMethod  로 등록한 메서드 실행.

