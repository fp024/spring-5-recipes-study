## 레시피 16-02-ii 단위/통합 테스트 작성하기

> ...

### 이번 레시피에서 확인해야할  내용

* ✔ **16-02-i**: 단일 클래스에 대한 단위 테스트 작성하기

* ✅ **16-02-ii**: **스텁 객체**를 써서 의존 관계가 있는 클래스에 대한 단위 테스트 작성하기

* ⬜ ...

  



## 진행

* 스텁 객체를 직접 작성해서 사용해보는 예제.

* assertEquals의 3번째 인자 delta(오차범위)를 사용할 때.. 

  ```java
  assertEquals(50, accountDaoStub.balance, 0);
  // assertj의 assertThat으로 다음과 같이 바꿀 수 있음.
  assertThat(accountDaoStub.balance).isCloseTo(50, within(0.0));
  ```

  * 그런데 오차범위가 0이면 delta값을 생략해도 될 것 같다. 😅







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

