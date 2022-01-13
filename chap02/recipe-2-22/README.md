## 레시피 2-22 AOP를 이용해 POJO를 도메인 객체에 주입하기

* 스프림 밖에서 만든 객체에 스프링 빈을 주입할 때 AOP를 사용하는 예제
* `@EnableSpringConfigured` 설정을 위해서는 spring-aspects 라이브러리를 추가한다.

* `@Configurable`

  클래스를 Spring 기반 구성에 적합한 것으로 표시합니다.

  일반적으로 AspectJ `AnnotationBeanConfigurerAspect`와 함께 사용됩니다.



처음에 aspect gradle 플러그인으로 되나 확인해보았는데,  이부분의 경우는 -javaagent 인자를 직접 전달 해주는 방식으로 해야 Complex의 @Autowired가 붙은 setFormatter가 정상 설정되었다.



확실히 특이한 방법이긴한데, 이렇게 사용할 상황이 있을지? 잘 모르겠다.
