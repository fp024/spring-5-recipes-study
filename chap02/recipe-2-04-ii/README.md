## 레시피 2-4-ii @Inject로 POJO 자동연결하기

* `@Inject`를 사용해서 자동연결
* 저자님 예제를 보니, `@Inject`를 사용하려고, `javax:javaee-api`을 추가해두셨던데, 덩달아 추가되는 디펜던시가 많다. 어노테이션만 추가하는 `javax.inject:javax.inject:1` 을 사용하는게 나을 것 같다. 
  * spring-context 5.1.x 까지 `javax.inject:javax.inject:1` 가 Optional 디펜던시 였음
* `@Inject`를 위한 커스텀 어노테이션에 붙인 `@Qualifier`는 스프링 것이 아닌 `javax.inject.Qualifier` 이다.
