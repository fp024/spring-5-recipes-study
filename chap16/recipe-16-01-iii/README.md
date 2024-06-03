## 레시피 16-01-iii JUnit과 TestNG로 단위 테스트 작성하기

> 16장 스프링 테스트 시작

### 이번 레시피에서 확인해야할  내용

* ✔ **16-01-i**: JUnit 4 테스트 코드 작성

* ✔ **16-01-ii**: TestNG 테스트 코드 작성

* ✅ **16-01-iii**: TestNG 에서 테스트 데이터 메서드를 정의해서 사용하는 방법



## 진행

저자님이 TestNG로 `@DataProvider` 사용하신 방식이 마치 JUnit 5의 `@ParameterizedTest` 사용한 것과 비슷하다.

```java
@ParameterizedTest
@CsvSource({
    "1, 1, 2",
    "2, 2, 4",
    // 추가 테스트 데이터...
})
void testAdd(int firstNumber, int secondNumber, int expectedResult) {
    assertEquals(expectedResult, firstNumber + secondNumber);
}

```




## 의견

* 


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

