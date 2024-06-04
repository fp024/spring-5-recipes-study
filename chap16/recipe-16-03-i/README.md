## 레시피 16-03-i 스프링 MVC 컨트롤러에 대한 단위 테스트 작성하기

> 예제를 미리보니... MockMvc를 사용한 것은 아니고, 
>
> 컨트롤러 객체 생성해서 Mock 서비스 주입해서 테스트 하는 형식이였다.

### 이번 레시피에서 확인해야할  내용

* ✅ **16-03-i**: 스프링 MVC 컨트롤러에 대한 단위 테스트 작성

  



## 진행

예전 Struts2 액션 테스트 했을 때 처럼, 컨트롤러 객채 생성하고, 컨트롤러 메서드에 전달한 ModelMap이 잘 설정되었는지 확인, 그리고, 뷰 이름이 잘 설정되었는지 확인하는 테스트를 했다.

```java
    // Execute
    String viewName = depositController.deposit(TEST_ACCOUNT_NO, TEST_AMOUNT, modelMap);

    assertThat(viewName).isEqualTo("success");
    assertThat(modelMap)
        .containsEntry("accountNo", TEST_ACCOUNT_NO)
        .containsEntry("balance", 150.0);
```

* assertj의 assertThat()을 사용하면 검증 메서드가 좀 더 정돈된 느낌이 든다. 👍



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

