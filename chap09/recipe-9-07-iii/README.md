## 레시피 9-07-ii 스프링에서 ORM 리소스 팩토리 구성하기 - 3

>  스프링에서 ORM 리소스 팩토리 구성하기 - 3 - LocalEntityManagerFactoryBean 사용

### 이번 레시피에서 확인해야할  내용

* ✔ 스프링에서 ORM 리소스 팩토리 구성하기 - 1 - LocalSessionFactoryBean 사용

* ✔ 스프링에서 ORM 리소스 팩토리 구성하기 - 2 - DataSource를 별도 생성해서 주입

* ✅ 스프링에서 ORM 리소스 팩토리 구성하기 - 3 - LocalEntityManagerFactoryBean 사용

  




## 진행

##### 레시피 9-07-iii

이번에는 LocalSessionFactoryBean 대신 LocalEntityManagerFactoryBean을 사용하도록 코드 변경

* LocalEntityManagerFactoryBean 에는 바로 DataSource 빈을 지정할 수 없어서, Hibernate Properties에 연결정보를 포함하고 `hibernate-hikaricp`라이브러리를 다시 추가했다.






## 의견

* ...



---

## 기타

#### Class의 이름을 얻을 때, getCanonicalName()와 getName()의 차이

* getCanonicalName(): 이 메소드는 클래스의 전체 이름을 반환합니다. 이는 패키지 이름과 클래스 이름을 포함하며, 내부 클래스의 경우에는 `.`으로 구분됩니다. 예를 들어, org.hibernate.hikaricp.internal.HikariCPConnectionProvider와 같은 형태입니다.
* getName(): 이 메소드는 클래스의 전체 이름을 반환하지만, 내부 클래스의 경우에는 `$`으로 구분됩니다.




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

