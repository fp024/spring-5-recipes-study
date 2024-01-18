## 레시피 9-01-i JDBC 템플릿으로 DB 수정하기

> 레시피 9-00-ii에서 insert 메서드에 JDBC Template 사용
>

### 이번 레시피에서 확인해야할  내용

* ✔ DAO의 insert 메서드에 JDBC Template 사용
  
* ⬜ `PreparedStatementCreator` 를 활용해서 insert() 수정



## 진행

##### 레시피 9-01-i

* `PreparedStatementCreator` 를 활용해서 insert() 수정
* 나는 JDBC 템플릿은 Bean으로 선언했다..😅





### 예제 실행

Main 클래스의 main을 실행하면 되므로 다음과 같이 실행해주면 된다.

이번에는 프로필로 분리해서, mysql, hsqldb 프로필로 개별 실행할 수 있게 했다.

#### MySQL 실행 (기본 설정 프로필)

```bash
gradle clean run
```

#### HSQLDB 실행 

```bash
gradle clean run -Dspring.profiles.active=hsqldb
```



그런데, JUnit 테스트를 수행하면 빈이 없다고 나와서 실패가 된다. 😂

```
gradle clean test
gradle clean test -Dspring.profiles.active=hsqldb
```

일단 커밋을 먼저하고 고쳐보자!






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

