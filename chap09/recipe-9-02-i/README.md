## 레시피 9-02-i JDBC 템플릿으로 DB 조회하기 - 1

> RowCallBackHandler를 사용해서 데이터 추출
>

### 이번 레시피에서 확인해야할  내용

* ✅  조회 부분(findByVehicleNo)을 JdbcTemplate 사용해서 변경 - RowCallBackHandler를 사용해서 데이터 추출




## 진행

##### 레시피 9-02-i

findByVehicleNo()

* 💡 코드가 바뀌고나서 결과 없을 때.. 빈객체를 반환하게 되어버려서,  vehicle_no 값이  null 일 때 null 을 반환하게 방어 코드를 넣었다.

findByVehicleNo() 를 변경하였는데, findAll()도 바꿔볼 수 있을 것 같다.

* findAll()은 단순하게 먼저 바꿔봄

  ```java
    // 잘못됨
    public List<Vehicle> findAll() {
      return jdbcTemplate.queryForList(SELECT_ALL_SQL, Vehicle.class);
    }
  ```

  결과 매핑을 특이하게하려면 어떻게해야할까? 그때는 쿼리를 쓰고  RowMapper 를 설정해줘야되는 것 같다.

  그런데 이거 잘못했다. 컬럼이 하나인 행일 때만 가능한 것 같다.

  ``` java
    // 정상
    @Override
    public List<Vehicle> findAll() {
      return jdbcTemplate.query(SELECT_ALL_SQL, (rs, rowNum) -> toVehicle(rs));
    }
  
    private Vehicle toVehicle(ResultSet rs) throws SQLException {
      return new Vehicle(
          rs.getString("vehicle_no"), //
          rs.getString("color"),
          rs.getInt("wheel"),
          rs.getInt("seat"));
    }
  ```

  * 그런데.. 나중 레시피에서 여러 Row 쿼리에서 할 것 같음..😅




## 의견

* 이번 레시피에서 딱히 이슈는 없었다. 



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

