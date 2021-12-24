## 레시피 2-4-i @Resource로 POJO 자동연결하기

* `@Resource`를 사용해서 자동연결
* `@Resource` 어노테이션 사용을 위해 아래 디펜던시가 필요했다.
  ```groovy
  implementation "javax.annotation:javax.annotation-api:${javaxAnnotationApiVersion}"
  ```
