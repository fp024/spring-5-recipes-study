## 레시피 9-04-iii JDBC 템플릿에서 기명 매개변수 사용하기 - 3

>  JDBC 템플릿에서 기명 매개변수 사용하기 - 3
>

### 이번 레시피에서 확인해야할  내용

* ✔ JDBC 템플릿에서 기명 매개변수 사용하기 - NamedParameterJdbcTemplate 사용
* ✔ JDBC 템플릿에서 기명 매개변수 사용하기 - MapSqlParameterSource 사용
* ✅ JDBC 템플릿에서 기명 매개변수 사용하기 - BeanPropertySqlParameterSource 사용




## 진행

##### 레시피 9-04-iii

* 이번 레시피에서는 `BeanPropertySqlParameterSource`를 사용하면서 코드가 더 단순해졌다. 😅

  ```java
    // ✨ 레시피 주제
    @Override
    public void insert(Collection<Vehicle> vehicles) {
      SqlParameterSource[] parameterSources =
          vehicles.stream() //
              .map(BeanPropertySqlParameterSource::new)
              .toArray(SqlParameterSource[]::new);
      namedParameterJdbcTemplate.batchUpdate(INSERT_SQL, parameterSources);
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

