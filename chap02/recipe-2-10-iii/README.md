## 레시피 2-10-iii 팩토리(정적 메서드, 인스턴스 메서드, 스프링 FactoryBean)로 POJO 생성하기

* 스프링 팩토리 빈으로 POJO 생성하기
  * `AbstractFactoryBean<T>`를 상속하는 팩토리 클래스를 만들어 getObjectType(), createInstance()를 구현하고 각각 빈으로 등록하여 사용.