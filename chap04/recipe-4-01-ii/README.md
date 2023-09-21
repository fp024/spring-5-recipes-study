## 레시피 4-01-ii REST 서비스로 XML 발행하기 (iii ~ iv 포함)

> ...
>

### 이번 레시피에서 확인해야할  내용

* @ResponseBody로 XML 만들기

  

## 진행

* 도메인 모델을 그대로 `@ResponseBody`로 반환하니, `4-01-i` 예제에서 설정했던 뷰 설정들은 제거해도 되었다.

그런데...

`http://localhost:8080/members.xml` 으로 접근하는 것은 안되던데...🙄

저자님은 `http://localhost:8080/members` 이렇게 하거나 xml이 아닌 다른 확장자를 넣으면 안된다고 하시는데..

내 환경에서는  `http://localhost:8080/members` 에서만 잘된다. (스프링 버전이 달라서 그런지...ㅠㅠ)

* Spring 버전이 달라서 그런게 맞는 것 같다.
  * `Spring 5.3.30`에서  `5.0.0.RELEASE`로 프로젝트 버전을 낮추니까 아래 두가지 URL이 동작함.
    * `http://localhost:8080/members`
    * `http://localhost:8080/members.xml`
  * ✨ 그런데 이렇게 일부러 버전을 낮춰서 사용할 필요는 없을 것 같다. 점점 보안 문제 때문에 알아서 해주는 일보단 직접 명시하는게 보편화 되는 것 같음.

차라리 컨트롤러에 직접 확장자를 고정으로 명시하는게 나음.

```java
  @RequestMapping(value = "/members.xml")
  @ResponseBody
  public Members getRestMembersXML() { ... }
```





## 의견

* ...



## 기타

### 4-01-iii 예제는 따로 안만들어도 되겠다.

그냥 이 예제에서 `@RestController`로만 바꾼 내용이다.

* 그에 따라 @ResponseBody를 따로 입력하지 않아도 됨.

### 4-01-iv 예제도 따로 안만들어도 되겠다.

* RestMemberController에 `@PathVariable`을 활용해만든 특정 회원 조회 API 하나만 추가된다.





## 정오표

* ...

