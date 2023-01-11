* ## 레시피 3-6-i 이름으로 뷰 해석하기 2

  

  ### 이번 레시피에서 확인해야할  내용

  ##### 리소스 번들에 따라 뷰 해석하기

  * ViewResolverConfiguration#viewResolver()

  * court-views.properties

    

  

  ## 진행

  * 이전 예제에서 XML에 설정한 내용을 court-views.properties에다 정의해서 사용했다.
  * 리소스 번들을 사용하므로, 로케일별로 설정을 따로 만들어 로드할 수 있음.

  

  


  ## 기타

  * `WebMvcConfigurer`를 구현한 설정에 특히 `@EnableWebMvc`를 붙여야할 것 같음.



### ResourceBundleViewResolver도 XmlViewResolver와 마찬가지로 Deprected되었다.

```
deprecated as of 5.3, in favor of Spring's common view resolver variants and/or custom resolver implementations
```

