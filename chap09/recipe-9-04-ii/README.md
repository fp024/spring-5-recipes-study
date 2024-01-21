## 레시피 9-04-ii JDBC 템플릿에서 기명 매개변수 사용하기 - 2

>  JDBC 템플릿에서 기명 매개변수 사용하기 - 2
>

### 이번 레시피에서 확인해야할  내용

* ✔ JDBC 템플릿에서 기명 매개변수 사용하기 - NamedParameterJdbcTemplate 사용
* ✅ JDBC 템플릿에서 기명 매개변수 사용하기 - MapSqlParameterSource 사용




## 진행

##### 레시피 9-04-ii

* 배치 INSERT 코드는 Generic 경고도 없어지고, 좀 더 명확해졌다.

  ```java
    // ✨ 레시피 주제
    @Override
    public void insert(Collection<Vehicle> vehicles) {
      SqlParameterSource[] parameterSources =
          vehicles.stream() //
              .map(v -> new MapSqlParameterSource(toParameterMap(v)))
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

