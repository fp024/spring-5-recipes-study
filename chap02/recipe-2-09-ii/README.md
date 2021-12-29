## 레시피 2-9-ii 후처리기를 만들어 POJO 검증 / 수정하기

* 2-9-i와의 차이가 `ProductCheckBeanPostProcessor `, `AuditCheckBeanPostProcessor` 위에 @Component를 붙이지않아 빈으로 등록하지 않는 것 말고는 차이가 없는데... 이 예제는 왜만드신 것인지? 모르겠다. BeanPostProcessor 들이 빠져서, 로그는 아래와 같다.

  ```
  03:31:36.425 [main] INFO  org.fp024.study.spring5recipes.shop.Product - 제품 객체 생성 - 이름 지정 없음
  03:31:36.428 [main] INFO  org.fp024.study.spring5recipes.shop.Product - AAA 객체의 PostConstruct 호출
  03:31:36.429 [main] INFO  org.fp024.study.spring5recipes.shop.Product - CD-RW 제품 객체 생성
  03:31:36.429 [main] INFO  org.fp024.study.spring5recipes.shop.Product - CD-RW 객체의 PostConstruct 호출
  03:31:36.430 [main] INFO  org.fp024.study.spring5recipes.shop.Product - DVD-RW 제품 객체 생성
  03:31:36.430 [main] INFO  org.fp024.study.spring5recipes.shop.Product - DVD-RW 객체의 PostConstruct 호출
  ```

  
