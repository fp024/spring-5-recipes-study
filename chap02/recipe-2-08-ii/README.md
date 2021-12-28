## 레시피 2-8-ii 어노테이션을 이용해 POJO 초기화/폐기 커스터마이징하기

* @PostConstruct와 @PreDestroy로 초기 / 폐기 메서드 작성하기.

* @Value 어노테이션으로 필드 값 할당하기

  ```java
  @Setter
  @Value(ShopFileUtils.FILE_NAME)
  private String fileName;
  
  @Value("#{systemProperties['java.io.tmpdir']}" + "/cashier")
  @Setter
  private String path;
  ```

  * 시스템 프로퍼티도 위와 같은 식으로 가져올 수 있다.

* 예제를 보니.. xml 설정과 annotation 설정을 번갈아가면서 써나가는 것 같은데... 레시피와 관련된 사항이 아니라면 어노테이션 기반으로 예제를 만들자!!!
