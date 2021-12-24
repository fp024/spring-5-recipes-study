## 레시피 2-6-i 외부 리소스(텍스트, XML, 프로퍼티, 이미지 파일)의 데이터 사용하기

* `@PropertySource`를 붙어 프로퍼티 파일을 로드하려면, `PropertySourcesPlaceholderConfigurer`를 빈으로 선언해야한다고 하는데,
  지금 내 환경에서는 빈으로 안만들어주어도 프로퍼티 값 로드에 문제는 없었다. (Spring 5.3.x)
  ```java
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
  ```


* `@PropertySource`와 `@Value` 어노테이션을 사용해서, 프로퍼티 값 사용하기, `:0` 부분은 값이 없을시 기본 값 0 설정.

  ```java
  // ...
  @PropertySource("classpath:discounts.properties")
  public class ShopConfiguration {
    // ...
    @Value("${specialcustomer.discount:0}")
    private double specialCustomerDiscountField;
    // ...
  ```
