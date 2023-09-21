## 레시피 4-02-i REST 서비스로 JSON 발행하기 (ii 포함)

> ...
>

### 이번 레시피에서 확인해야할  내용

* 스프링 REST 서비스로 JSON 객체를 발행

  

## 진행

### JSON Path로 테스트 검증 할 때는 다음 라이브러리가 필요함.

```groovy
implementation "com.jayway.jsonpath:json-path:${jsonPathVersion}"
```

* 대상 JSON

  ```json
  {
    "members" : {
      "members" : [ {
        "name" : "Marten Deinum",
        "phone" : "00-31-1234567890",
        "email" : "marten@deinum.biz"
      }, {
        "name" : "John Doe",
        "phone" : "1-800-800-800",
        "email" : "john@doe.com"
      }, {
        "name" : "Jane Doe",
        "phone" : "1-801-802-803",
        "email" : "jane@doe.com"
      } ]
    }
  }
  ```

  XML 도메인을 만드는 코드가 아직 유지상태라.. members가 두번 감싸져 있음 😂

  

* 테스트

  ```java
    @Test
    void testGetRestMembers() throws Exception {
      mockMvc
          .perform(get("/members")) //
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.members.members[0].name").value("Marten Deinum"));
    }
  ```



### 💡 확장자로 컨텐트 협상하는 것 자체가 폐기됨.

> ### ContentNegotiationManagerFactoryBean 클래스 주석
>
> Deprecation Note: 5.2.4 버전부터는 경로 확장자를 사용하여 컨텐츠 협상과 요청 매핑을 하는 것을 권장하지 않기 위해 favorPathExtension과 ignoreUnknownPathExtensions 옵션이 폐기되었습니다. RequestMappingHandlerMapping에서도 유사한 옵션이 폐기되었습니다. 자세한 내용은 이슈 #24719를 참고하세요.

* https://github.com/spring-projects/spring-framework/issues/24179

  > - **스프링 MVC의 URL 패턴 변경**: 이 이슈는 스프링 MVC에서 URL 패턴에 `.json` 확장자를 사용하여 JSON 뷰를 반환하는 방식을 공식적으로 폐기하는 것에 관한 것입니다. 이 방식은 스프링 5 이후부터는 `@RestController` 어노테이션을 사용하여 JSON 뷰를 반환하는 것이 일반적이기 때문입니다.
  > - **스프링 MVC의 `@RestController` 사용법**: `@RestController`는 `@Controller`와 `@ResponseBody`를 합친 것으로, 컨트롤러 메서드의 반환값을 HTTP 응답 본문에 직접 작성합니다. 따라서 `@RestController`를 사용하면 메서드의 반환값이 JSON 형태로 HTTP 응답 본문에 작성되므로, 별도의 뷰 리졸버가 필요하지 않습니다.
  > - **스프링 MVC의 `@RequestMapping` 사용법**: `@RequestMapping` 어노테이션은 요청을 처리하는 메서드에 적용되는 어노테이션입니다. 이 어노테이션은 요청의 URL, HTTP 메소드, 파라미터, 헤더 등을 지정할 수 있습니다. `@RequestMapping` 어노테이션은 `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping` 등의 특화된 어노테이션으로 대체할 수 있습니다.

결국 url path를 명시적으로 설정하고, produces로 조절하는게 좋은 것 같다.

````java
  @RequestMapping(
      value = {"/members.xml"},
      produces = {MediaType.APPLICATION_XML_VALUE})
  public String getRestMembersXml(Model model) {
    processModel(model);
    return "xmlMemberTemplate";
  }

  @RequestMapping(
      value = {"/members", "/members.json"},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public String getRestMembersJson(Model model) {
    processModel(model);
    return "jsonMemberTemplate";
  }
````

JSON 반환 URL은 2개 경로여서   `@ParameterizedTest`를 사용해서 테스트를 다음처럼 할 수도 있음.

```java
  @ParameterizedTest
  @ValueSource(strings = {"/members", "/members.json"})
  void testGetRestMembersJson(String url) throws Exception {
    mockMvc
        .perform(get(url)) //
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.members.members[0].name").value("Marten Deinum"));
  }
```





## 의견

* ...



## 기타

### 4-02-ii 예제 내용 포함

* XML과 JSON 동시에 사용한 부분도 같이 포함됨.

  * 스프링 5.0.5.RELEASE 버전이라서 RequestMapping에 `/members` 만 적어도 확장자를 통해 자동 매핑을 할 수 있었는데, 5.2.4 부터는 그렇게 하지 않는것이 기본 값이 된 것 같다. 난 위에 적은대로 명시적으로 적어줬음.

    



## 정오표

* 258쪽
  * MappingJackson2JsonView로 `XML` 만들기 >> MappingJackson2JsonView로 `JSON` 만들기


