## 레시피 2-22 AOP를 이용해 POJO를 도메인 객체에 주입하기

* 스프림 밖에서 만든 객체에 스프링 빈을 주입할 때 AOP를 사용하는 예제
* `@EnableSpringConfigured` 설정을 위해서는 spring-aspects 라이브러리를 추가한다.

* `@Configurable`

  클래스를 Spring 기반 구성에 적합한 것으로 표시합니다.

  일반적으로 AspectJ `AnnotationBeanConfigurerAspect`와 함께 사용됩니다.



처음에 aspect gradle 플러그인으로 되나 확인해보았는데,  이부분의 경우는 -javaagent 인자를 직접 전달 해주는 방식으로 해야 Complex의 @Autowired가 붙은 setFormatter가 정상 설정되었다.



확실히 특이한 방법이긴한데, 이렇게 사용할 상황이 있을지? 잘 모르겠다.



---

### aspect 경고

```
[AppClassLoader@2b193f2d] error can't determine superclass of missing type org.springframework.transaction.interceptor.TransactionAspectSupport
 [Xlint:cantFindType]
[AppClassLoader@2b193f2d] error can't determine annotations of missing type org.springframework.transaction.annotation.Transactional
when weaving type org.fp024.study.spring5recipes.calculator.Main
when weaving classes
when weaving
...
```

이런 경고가 나와서... 아래 디펜던시 추가

```groovy
// aspect 컴파일러 경고 때문에 추가. 예제에서 트랜젝션이나 DB를 사용하진 않음.
implementation "org.springframework:spring-tx:${springVersion}"
```

