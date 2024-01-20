## 레시피 9-04-i JDBC 템플릿에서 기명 매개변수 사용하기

>  JDBC 템플릿에서 기명 매개변수 사용하기
>

### 이번 레시피에서 확인해야할  내용

* ✅ JDBC 템플릿에서 기명 매개변수 사용하기 - NamedParameterJdbcTemplate 사용

  




## 진행

##### 레시피 9-04-i

NamedParameterJdbcTemplate를 사용해서 insert, update 메서드를 수정했다.




## 의견

batchUpdate() 의 경우는 수정이 NamedParameterJdbcTemplate에 맞게 수정이 되어있지 않아서  다음과 같이 수정했다.

```java
  @SuppressWarnings("unchecked")
  @Override
  public void insert(Collection<Vehicle> vehicles) {
    List<Map<String, Object>> paramList =
        vehicles.stream().map(this::toParameterMap).collect(Collectors.toList());
    namedParameterJdbcTemplate.batchUpdate(
        INSERT_SQL, paramList.toArray(new Map[paramList.size()]));
  }
```

그런데 다음처럼 쓸 수도 있다.

```java
  @SuppressWarnings("unchecked")
  @Override
  public void insert(Collection<Vehicle> vehicles) {
    Map<String, ?>[] paramList =
        vehicles.stream() //
            .map(this::toParameterMap)
            .toArray(size -> new Map[vehicles.size()]);

    namedParameterJdbcTemplate.batchUpdate(INSERT_SQL, paramList);
  }
```

```java
  // ✨ 레시피 주제
  @SuppressWarnings("unchecked")
  @Override
  public void insert(Collection<Vehicle> vehicles) {
    Map<String, ?>[] paramList =
        vehicles.stream() //
            .map(this::toParameterMap)
            .toArray(Map[]::new); // ✨ 이렇게 해도 배열의 크기가 자동으로 정해짐.
    namedParameterJdbcTemplate.batchUpdate(INSERT_SQL, paramList);
  }
```





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

