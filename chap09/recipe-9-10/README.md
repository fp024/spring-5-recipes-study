## 레시피 9-10 스프링 데이터  JPA로 JPA 코드 간소화하기

>  스프링 데이터  JPA로 JPA 코드 간소화하기

### 이번 레시피에서 확인해야할  내용

* ✅ 스프링 데이터  JPA로 JPA 코드 간소화하기

  


## 진행

##### 레시피 9-10

* 디펜던시 추가

  ```groovy
  implementation "org.springframework.data:spring-data-jpa:${springDataJpaVersion}"
  ```

* Jpa 리포지터리 스캔

  ```java
  @EnableJpaRepositories("org.fp024.study.spring5recipes.course.dao")
  ```

* 리포지토리 인터페이스 작성

  ```java
  @Repository
  public interface CourseRepository extends CrudRepository<Course, Long> {}
  ```






## 의견

이걸로... 9장을 마쳤다.

그래도 원래 알고 있던 내용이 많아서 수월하게 했지만, 내용을 정리하는데 도움이 많이 되었다. 😊👍

그냥 mybatis로도 프로젝트를 만들까? 😅



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

