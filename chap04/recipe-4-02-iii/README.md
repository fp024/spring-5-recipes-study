## 레시피 4-02-iii REST 서비스로 JSON 발행하기 

> ...
>

### 이번 레시피에서 확인해야할  내용

* @ResponseBody로 JSON 만들기

  

## 진행

* 별로 고칠 것은 없었다.. 그냥 뷰 설정제거하고 컨트롤러 메서드에서 ResponseEntity로 반환하게 바꾼거 정도 말고 없다. 😅



## 의견

* ...



## 기타

### JSON 메시지 컨버터의 setPrettyPrint 설정

스프링이 자동으로 구성해주는 MappingJackson2HttpMessageConverter를 뽑아내서 `setPrettyPrint` 설정을 true로 하려고 했는데 안됨.

```java
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    LOGGER.info("### {} ###", converters); // 이때 리스트가 비어있음 의미가 없음.
    converters.stream()
        .filter(c -> c.getClass() == MappingJackson2HttpMessageConverter.class)
        .forEach(f -> ((MappingJackson2HttpMessageConverter) f).setPrettyPrint(true));
  }
```

이렇게 해야함.

```java
  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    LOGGER.info("### {} ###", converters); // 로그상으로 스프링에 의해 등록된 메시지 컨버터들이 보인다.
    converters.stream()
        .filter(c -> c instanceof AbstractJackson2HttpMessageConverter)
        .forEach(f -> ((AbstractJackson2HttpMessageConverter) f).setPrettyPrint(true));
  }
```

그런데.. AbstractJackson2HttpMessageConverter의 JavaDoc을 보니.. 다음과 같은 말이 있던데...

```
Compatible with Jackson 2.9 to 2.12, as of Spring 5.3.
```

지금 Jackson을 2.15.2로 쓰고 있음 😂 그냥 쓰자..



## 정오표

* ...

