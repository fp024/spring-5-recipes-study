## 레시피 9-02-i i JDBC 템플릿으로 DB 조회하기 - 2

> RowMapper로 데이터 추출하기
>

### 이번 레시피에서 확인해야할  내용

* ✔ 조회 부분(findByVehicleNo)을 JdbcTemplate 사용해서 변경 - RowCallBackHandler를 사용해서 데이터 추출
* ✅ RowMapper 로 데이터 추출하기 findByVehicleNo(), findAll()




## 진행

##### 레시피 9-02-ii

queryForObject 는 반드시 결과가 1개 있을 것으로 기대하기 때문에,  `EmptyResultDataAccessException` 예외를 처리했다.

```java
  @Override
  public Vehicle findByVehicleNo(String vehicleNo) {
    try {
      // ✨: 레시피 주제
      return jdbcTemplate.queryForObject(SELECT_ONE_SQL, new VehicleRowMapper(), vehicleNo);
    } catch (EmptyResultDataAccessException e) {
      // queryForObject는 결과가 반드시 1개 있을 것을 간주하기 때문에, 예외 처리를 해둔다.
      return null;
    }
  }
```




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
* 💡
* ...

