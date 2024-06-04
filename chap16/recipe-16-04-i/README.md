## 레시피 16-04-i 통합 테스트 시 애플리케이션 컨텍스트 관리하기

> 또 예제를 미리 보았는데, 지금 시점에는 사용하지 않을 만한 클래스를 사용하는 부분이 보인다.
>
> 현 시점에 맞게 바꿔쓰자!

### 이번 레시피에서 확인해야할  내용

* ✅ **16-04-i**: JUnit에서 테스트 컨텍스트 프레임워크 가져오기 - 

* ✖️ **16-04-i**: JUnit에서 테스트 컨텍스트 프레임워크 가져오기 - 

* ⬜ ...

  



## 진행

### 16-04-i

스프링 5의 현시점 마지막 버전은 ... 5.3.x 버전대인데..  JUnit 5와 함께 사용하면...

```java
@SpringJUnitConfig(classes = BankConfiguration.class)
class AccountServiceJUnit5ContextTests {

  private static final String TEST_ACCOUNT_NO = "1234";

  @Autowired private AccountService accountService;
  // ....
```

그냥 `@SpringJUnitConfig`를 써주면 간단하게 된다. 



### 16-04-ii

`AbstractJUnit4SpringContextTests`를 활용한 예제인데... 이 예제는 그냥 건너뛰어도 될 것 같다. 😅

* 그런데 이 클래스가 Spring 6.1.x에서도 여전이 살아있음 👍
  * https://github.com/spring-projects/spring-framework/blob/6.1.x/spring-test/src/main/java/org/springframework/test/context/junit4/AbstractJUnit4SpringContextTests.java





## 의견

* ...




---

## 기타

* ...





## 정오표

* ...
  


---

## JavaDocs

* ...



---

### 문서 사용 아이콘

* ✅: Current Done
* ✔: Done
* ⬜: TODO
* ✖️: Skip
* ...

