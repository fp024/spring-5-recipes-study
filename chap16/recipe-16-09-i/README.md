## 레시피 16-09-i 스프링 MVC 컨트롤러에 대한 통합 테스트 작성하기

> ...

### 이번 레시피에서 확인해야할  내용

* ✅ **16-09-i**:  MockMvc를 사용한 컨트롤러 테스트

  

  


## 진행

저자님은 

MVC 테스트에는 TestNG를 사용하셨고 (AbstractTransactionalTestNGSpringContextTests 상속) , 

16-10의 RestClient 코드까지 포함하셨는데..

JUnit 5로만 사용하고, 레시피 16-10에 대한 내용은 제거하고 별도 프로젝트로 분리했다.



## 의견

* ...




---

## 기타

### Mvc 매처가 hamcrest를 요구하므로 추가해주자!

```groovy
testImplementation (libs.hamcrest)
```



### 테스트 클래스에 `@Sql`을 쓸 때는  `@Transactional`을 같이 써줘야 동작했다.

```java
...
@Transactional
@Sql(scripts = "classpath:/bank.sql")
class DepositControllerJUnitContextTests {
  ...
```





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

