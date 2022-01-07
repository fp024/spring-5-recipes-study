## 레시피 2-13-i 애너테이션을 활용해 애스펙트 지향 프로그래밍하기

* AspectJ를 사용하고, @Before 어드바이스를 사용하는 예제

* 디펜던시 추가

  ```groovy
  implementation "org.aspectj:aspectjweaver:${aspectjVersion}"
  
  // Spring AOP로 사용하면 AspectJ의 어노테이션만 사용하기 때문에 
  // aspectjrt가 디펜던시에 없어도 잘 동작함.
  // implementation "org.aspectj:aspectjrt:${aspectjVersion}"
  ```

* CalculatorConfiguration 구성 클래스에` @EnableAspectJAutoProxy` 붙임

* Aspect 클래스

  * CalculatorLoggingAspect 클래스에는 `@Aspect` 어노테이션을 붙이고, Aspect의 어노테이션을 사용해 구성

* 예제가 하는 일은 메서드 실행전에 로그를 찍음

* 이 예제 프로젝트는 AspectJ의 어노테이션 사용을 위해 `aspectjweaver` 를 추가하긴 했지만, 내부적으로는 순수 Spring AOP로 동작함. 

  * spring-context가 spring-aop를 의존관계로 끌어옴.




* **Aspect:** 어디서(**Pointcut**) 무엇을 할 것 인지(**Advice**) 를 합쳐놓은 개념

* **Pointcut:** Advice에 적용할 타입 및 객체를 찾는 표현식
* **JoinPoint:** Pointcut으로 매치한 실행지점

