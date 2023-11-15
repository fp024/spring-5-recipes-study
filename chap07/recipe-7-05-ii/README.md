## 레시피 7-05-ii 메서드 호출 보안하기 - 애너테이션 + 표현식으로 메서드 보안하기

> 이번엔 좀더 복잡한 조건을 표현식을 통해 설정할 수 있음.

### 이번 레시피에서 확인해야할  내용

* ✔ 애너테이션을 붙여 메서드 보안하기

* ✅ 애너테이션 + 표현식으로 메서드 보안하기

* ✅ 애너테이션 + 표현식으로 거르기
  


## 진행

7-05-ii ~ 7-05-iii 내용을 모두 포함했다.

### 7-05-ii

```java
  @Override
  @PreAuthorize("hasAuthority('USER')")
  @PostAuthorize("hasAuthority('ADMIN') or returnObject.owner == authentication.name")
  public Todo findById(long id) {
    return todoRepository.findOne(id);
  }
```

`@PostAuthorize`를 활용해서 ADMIN 권한이면 어떤 Todo든 조회가능하거나

조회된 결과가 자신이 소유한 Todo가 아닐 경우 Access Denied 예외 발생



### 7-05-iii

```java
  @Override
  @PreAuthorize("hasAuthority('USER')")
  @PostFilter("hasAuthority('ADMIN') or filterObject.owner == authentication.name")
  public List<Todo> listTodos() {
    return todoRepository.findAll();
  }
```

`@PostFilter`를 활용해서 ADMIN 권한이면 모든 내용 반환

그렇지 않을 경우 일치하는 Todo의 소유자와 로그인 유저의 이름이 같을 때만 반환

> 👿 편하게 쓸 수는 있지만 결과 데이터가 많을 경우는 문제가 됨.




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
* ...

