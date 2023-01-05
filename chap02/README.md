# Chapter 2 스프링 코어



## 진행간 이슈

p59 ~ 60

* 60쪽에서 AnnotationConfigApplicationContext 의 생성자 인자로 패키지 명을 전달해주는데,  이럴 경우, 59쪽의 클래스 이름으로 정의한 include 필터링의 의미가 없어진다. 예제를 아래처럼 exclude 설정으로 만드는것이 나았을 것 같다.

  ```java
  @Configuration
  @ComponentScan(
      basePackages = "org.fp024.study",
      excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = {".*sequence.*ExcludeBean"})
      })
  public class SequenceGeneratorConfiguration {}
  ```

  패키지 전달도 하지 말고, 설정 클래스를 인자로 전달하고, `@ComponentScan`에서 basePackages를 정해주는 것이 더 나을 것 같다.

  위 예에서는 ExcludeBean 란 클래스에  `@Component("excludeBean")` 어노테이션이 붙어있지만, exclude 필터 설정에 의해 빈으로 생성되지 않는다.

* AnnotationConfigApplicationContext 의  `getBeanDefinitionNames()` 메서드로 생성된 빈 이름을 볼 수 있다.





## 정오표

* p54
  * 예제에서 seqgen.setInitial(`"100000"`); => seqgen.setInitial(`100000`);

