## 레시피 16-02-iii 단위/통합 테스트 작성하기

> ...

### 이번 레시피에서 확인해야할  내용

* ✔ **16-02-i**: 단일 클래스에 대한 단위 테스트 작성하기

* ✔ **16-02-ii**: **스텁 객체**를 써서 의존 관계가 있는 클래스에 대한 단위 테스트 작성하기

* ✅ **16-02-iii**: **Mock 객체**를 써서 의존 관계가 있는 클래스에 대한 단위 테스트 작성하기

* ⬜ ...

  



## 진행

* Mock 객체를 직접 작성해서 사용해보는 예제.



#### Mockito 라이브러리 추가

```groovy
  testImplementation (libs.mockito.core)
  testImplementation (libs.mockito.junit.jupiter)
```

나는 `@Mock` 어노테이션을 붙여서 Mock을 자동생성하기 위해서 `mockito-junit-jupiter` 도 추가했다.

```java
@ExtendWith(MockitoExtension.class) // (1)
class AccountServiceImplMockTests {
  // ...
  @Mock private AccountDao accountDao;  // (2)
  private AccountService accountService;

  @BeforeEach
  public void init() {
    accountService = new AccountServiceImpl(accountDao);
  }
  // ...
```

1. `MockitoExtension` 추가
2. Mock으로 만들 필드에 `@Mock` 붙임

* 💡AccountService에 `@InjectMocks`를 붙여서 자동으로 accountDao를 주입해줄 수 있지 않을까? 생각할 수 도 있는데...

  * AccountService가 인터페이스이기 때문에 다음과 같이 필드에서 초기화를 해줘야함.

    ```java
      private AccountService accountService = ew AccountServiceImpl();
    ```

  * 그러나 AccountServiceImpl가 accountDao를 파라미터로 받는 생성자 1개 밖에 없으므로 여기서는 @BeforeEach 지정 메서드에서 따로 넣어줄 수 밖에 없음.





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

* ✅
* ✔
* ⬜
* ...

