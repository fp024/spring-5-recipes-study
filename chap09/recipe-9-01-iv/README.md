## 레시피 9-01-iv JDBC 템플릿으로 DB 수정하기 - 4

> 레시피 9-01-iii의 JDBC Template 사용처를 SQL 문과 매개변수 값 전달 방식으로 수정
>

### 이번 레시피에서 확인해야할  내용

* ✔ `PreparedStatementCreator` 를 활용해서 insert() 수정

* ✔  JDBC Template 사용처를 `PreparedStatementCreator`를 사용한 람다식으로 변경

* ✔  JDBC Template 사용처를 `PreparedStatementSetter`를 사용한 람다식으로 변경

* ✅  JDBC Template 사용처를 SQL 문과 매개변수 값 전달 방식으로 수정




## 진행

##### 레시피 9-01-iv

#### JDBC Template 사용처를 SQL 문과 매개변수 값으로 수정

```java
  // 이전: PreparedStatementSetter
  @Override
  public void insert(Vehicle vehicle) {
    jdbcTemplate.update(INSERT_SQL, ps -> prepareStatement(ps, vehicle));
  }
```

```java
  private static final String INSERT_SQL =
      """
      INSERT INTO vehicle (color, wheel, seat, vehicle_no)
      VALUES (?, ?, ?, ?)
      """;

  // 변경: 매개변수 값 전달:  ?의 순서대로 전달
  @Override
  public void insert(Vehicle vehicle) {
    jdbcTemplate.update(INSERT_SQL, ps -> prepareStatement(ps, vehicle));
  }
```








## 의견

* ...



---

## 기타







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

