## 레시피 4-01-i REST 서비스로 XML 발행하기

> 코드 정리를 하면서 진행하자.. 이전 예제 했던 코드들이 남아있으면 해깔림 😂
>

### 이번 레시피에서 확인해야할  내용

* 스프링에서 XML 기반의 RESET 서비스 발행

  

## 진행

* XML 출력을 위해 필요한 라이브러리가 있었는데... 아래 2개의 라이브러리가 필요했다.

  ```java
    implementation "javax.xml.bind:jaxb-api:${jaxbApiVersion}"
    implementation "org.glassfish.jaxb:jaxb-runtime:${jaxbRuntimeVersion}"
  ```

  * 버전은 `2.3.1`이 마지막 javax 지원 버전인 것 같다.

* `@PostConstract` 사용을 위해 필요

  ```java
  implementation "javax.annotation:javax.annotation-api:${javaxAnnotationApiVersion}"
  ```

* 이번 예제의 주제와는 관련 없지만...

  설정 클래스를 RootConfiguration과 ServletConfiguration으로 나눴다.

  ```java
  // include 필터 설정을 해서 확실히 필요한 것만 스캔하려면...
  // useDefaultFilters를 false로 해야한다.
  @ComponentScan(
      basePackages = "org.fp024.study.spring5recipes.court",
      useDefaultFilters = false,
      includeFilters = {
        @Filter(type = FilterType.ANNOTATION, classes = Controller.class),
      })
  public class ServletConfiguration implements WebMvcConfigurer {
    ...
  }
  ```

* XML 검증

  *  XML 응답 데이터

    ```xml
    <members>
        <member>
            <email>marten@deinum.biz</email>
            <name>Marten Deinum</name>
            <phone>00-31-1234567890</phone>
        </member>
        <member>
            <email>john@doe.com</email>
            <name>John Doe</name>
            <phone>1-800-800-800</phone>
        </member>
        <member>
            <email>jane@doe.com</email>
            <name>Jane Doe</name>
            <phone>1-801-802-803</phone>
        </member>
    </members>
    ```

  * 검증

    ```java
      @Test
      void testSetupForm() throws Exception {
        mockMvc
            .perform(get("/members")) //
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(model().attributeExists("members"))
            .andExpect(content().contentType(MediaType.APPLICATION_XML))
            .andExpect(xpath("/members/member[3]/name").string("Jane Doe"));
      }
    ```

    xpath가 뭔가 했더니 생각보다 단순하다. 👍
    별도 라이브러리 추가 없이 검증이 가능했다.

## 의견

* ...



## 기타

* ...



## 정오표

* ...

