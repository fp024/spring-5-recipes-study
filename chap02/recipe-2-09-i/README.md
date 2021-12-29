## 레시피 2-9-i 후처리기를 만들어 POJO 검증 / 수정하기

* 모든 빈 인스턴스를 처리하는 후처리기 생성하기

* 로그를 찍어 보았을 때, 빈 초기화 이후 @PostConstruct가 붙은 메서드 전후로 호출이 되었다.

  ```
  03:17:29.171 [main] INFO  org.fp024.study.spring5recipes.shop.Product - 제품 객체 생성 - 이름 지정 없음
  In ProductCheckBeanPostProcessor.postProcessBeforeInitialization, processing Product: AAA
  03:17:29.174 [main] INFO  org.fp024.study.spring5recipes.shop.Product - AAA 객체의 PostConstruct 호출
  In ProductCheckBeanPostProcessor.postProcessAfterInitialization, processing Product: AAA
  03:17:29.175 [main] INFO  org.fp024.study.spring5recipes.shop.Product - CD-RW 제품 객체 생성
  In ProductCheckBeanPostProcessor.postProcessBeforeInitialization, processing Product: CD-RW
  03:17:29.176 [main] INFO  org.fp024.study.spring5recipes.shop.Product - CD-RW 객체의 PostConstruct 호출
  In ProductCheckBeanPostProcessor.postProcessAfterInitialization, processing Product: CD-RW
  03:17:29.176 [main] INFO  org.fp024.study.spring5recipes.shop.Product - DVD-RW 제품 객체 생성
  In ProductCheckBeanPostProcessor.postProcessBeforeInitialization, processing Product: DVD-RW
  03:17:29.177 [main] INFO  org.fp024.study.spring5recipes.shop.Product - DVD-RW 객체의 PostConstruct 호출
  In ProductCheckBeanPostProcessor.postProcessAfterInitialization, processing Product: DVD-RW
  ```
  
* 예전에 기억하기로는.. @PostConstruct 메서드가 private 이면 오류가 났던 것 같은데.. 현재환경에서는 잘된다.

  ```java
  @PostConstruct
  private void postConstruct() {
    LOGGER.info("{} 객체의 PostConstruct 호출", name);
  }
  ```

  
