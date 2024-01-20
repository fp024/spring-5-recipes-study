## 레시피 9-02-vi JDBC 템플릿으로 DB 조회하기 - 6

>  단일값 쿼리하기
>

### 이번 레시피에서 확인해야할  내용

* ✔ 조회 부분(findByVehicleNo)을 JdbcTemplate 사용해서 변경 - RowCallBackHandler를 사용해서 데이터 추출
* ✔ RowMapper 로 데이터 추출하기 findByVehicleNo(), findAll()
* ✔ BeanPropertyRowMapper 사용해보기
* ✔ queryForList()로 여러 row 쿼리하기
* ✅ 단일 값 쿼리하기 




## 진행

##### 레시피 9-02-vi

* 단일 값( Column 1개)을 받는다면 queryForObject()의 두번째 인자로 requredType  을 설정해서 전달해주면 된다.




## 의견

* queryForObject() 많이 쓰게 되면서...  조회 결과가 없을 때.. `EmptyResultDataAccessException`를 확인해서 null을 반환하는게 맞는지.. 그냥 예외 발생하게 두는게 나은지 좀 해깔릴 때가 있다.

  * DAO에서의 예외처리는 지우고 서비스에서 처리하는게 나을 것 같기도하다.
  
  

---

## 기타

#### SonarLint 규칙이 특이한 부분

**Only one method invocation is expected when testing runtime exceptions (java:S5778)**

다음 코드는 SonarLint  경고가 발생한다. assertThatThrownBy 예외 발생 확인 람다 블록에서 메서드 호출을 여러번 했다고 나는데...😅

```java
    assertThatThrownBy(() -> vehicleDao.findByVehicleNo(v1001.getVehicleNo()))
        .isInstanceOf(EmptyResultDataAccessException.class);
```

아래 처럼 바꾸면 경고가 없어진다.

```java
    var vehicleNo = v1001.getVehicleNo();
    assertThatThrownBy(() -> vehicleDao.findByVehicleNo(vehicleNo))
        .isInstanceOf(EmptyResultDataAccessException.class);
```

> `v1001.getVehicleNo()`의 호출도 메서드 호출한 것으로 봐서 경고가 나온듯 한데..
>
> 그런데 이부분은 도메인의 getter를 호출한 것이라 예외가 발생할 일이 없는데, 😅 검사를 철저하게 하는 것 같다. 😅





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

