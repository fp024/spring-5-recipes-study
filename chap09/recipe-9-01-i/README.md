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
gradle clean run -Dspring.profiles.active=hsqldb
```

* 프로필 파라미터 옵션을 추가하지 않으면 기본이 MySQL로 실행되게 했다.



#### HSQLDB 실행 

```bash
gradle clean run -Dspring.profiles.active=hsqldb
```



그런데, JUnit 테스트를 수행하면 빈이 없다고 나와서 실패가 된다. 😂

```
gradle clean test
gradle clean test -Dspring.profiles.active=mysql
gradle clean test -Dspring.profiles.active=hsqldb
```

일단 커밋을 먼저하고 고쳐보자!

수정을 했고..

> 원인을 찾아보니 뭔가 오타가 많았다.
>
> 프로필 설정시 다음과 같이 써야하는데, `spring.profiles.active`
>
> `spring.active.profiles` 이런 식으로 쓰기도 했었고, (이래서 2장에 활성 프로필 가져오는 부분을 잘못작성했었음.😂 - 이부분 같이 수정)
>
> 공통 설정 상속을 빼먹기도 했음...

이제 잘된다. 😊👍






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

