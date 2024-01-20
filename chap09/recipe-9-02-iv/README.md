## 레시피 9-02-iv JDBC 템플릿으로 DB 조회하기 - 4

>  여러  row 쿼리 하기
>

### 이번 레시피에서 확인해야할  내용

* ✔ 조회 부분(findByVehicleNo)을 JdbcTemplate 사용해서 변경 - RowCallBackHandler를 사용해서 데이터 추출
* ✔ RowMapper 로 데이터 추출하기 findByVehicleNo(), findAll()
* ✔ BeanPropertyRowMapper 사용해보기
* ✅  queryForList()로 여러 row 쿼리하기




## 진행

##### 레시피 9-02-iv

* `List<Map<String, Object>>`를 반환하는 queryForList() 를 사용하는 코드로 변경




## 의견

* ...



---

## 기타

* 레시피 9-02-v 는 따로 프로젝트를 만들지 않아도 될 것 같다.

> 코드 상 차이가 없다.
>
> 나는 v에서 `BeanPropertyRowMapper`를 사용해보는 예제가 나올 줄 알았는데.. 변경이 없었음.



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

