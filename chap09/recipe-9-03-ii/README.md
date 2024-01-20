## 레시피 9-03-ii JDBC 템플릿을 간단하게 생성하기

>  JdbcDAOSupport 클래스 상속하기
>

### 이번 레시피에서 확인해야할  내용

* ✔ JDBC  템플릿 주입 (그동안 항상 이렇게 했음)
* ✅ JdbcDAOSupport 클래스 상속하기




## 진행

##### 레시피 9-03-ii

* `JdbcVehicleDao`에 `JdbcDAOSupport`를 상속해봄.




## 의견

* ...

  

---

## 기타

#### SonarLint 에서 `getJdbcTemplate().xxx()`  형식으로 사용한 부분에 Null pointers should not be dereferenced (java:S2259) 경고가 나타남.

JdbcDaoSupport 클래스의 getJdbcTemplate() 에 `@Nullable`이 붙어 있어서 그런 것 같은데..

``` java
	/**
	 * Return the JdbcTemplate for this DAO,
	 * pre-initialized with the DataSource or set explicitly.
	 */
	@Nullable
	public final JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}
```

나는 설정 클래스에서 반드시 설정을 하므로 `Objects.requireNonNull(getJdbcTemplate()).xxx()` 형태로 바꿨다.

``` java
  @Override
  public void insert(Vehicle vehicle) {
    Objects.requireNonNull(getJdbcTemplate())
        .update(
            INSERT_SQL,
            vehicle.getColor(),
            vehicle.getWheel(),
            vehicle.getSeat(),
            vehicle.getVehicleNo());
  }
```

SonarLint 의 경고를 피하기 위해서 넣긴 했는데.. 그냥 null 검사가 추가된 것일 뿐임...😅



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

