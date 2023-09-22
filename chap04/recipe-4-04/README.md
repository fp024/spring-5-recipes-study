## 레시피 4-04 RSS/아톰 피드 발행하기

> ...
>

### 이번 레시피에서 확인해야할  내용

* ATOM, RSS 피드 생성 확인

  

## 진행

* ATOM, RSS 결과가 제대로 나오는지만 확인해보았다.
* JSON같은 경우는 RestTemplate으로 응답받을 때.. 다시 도메인으로 다시 역직렬화를 해봄.



## 의견

* 277쪽에서 .atom 으로 접속가능하다는 내용은 확장자로 자동 컨텐트 협상하는게 빠진이상 5.3.30에서는 안될 것 같다.
* 이것으로 4장 스프링 REST의 내용을 모두 확인했다. 👍
  * 이제 다음장이 어려울 것 같은데...... 😂




## 기타

### 기본 생성자가 없는 도메인을 역직렬화하려할 때.. 

`@JsonCreator`를 쓰면 잘되긴 했다.

```java
  @JsonCreator(mode = Mode.PROPERTIES)
  public TournamentContent(
      @JsonProperty("author") String author,
      @JsonProperty("publicationDate") Date publicationDate,
      @JsonProperty("name") String name,
      @JsonProperty("link") String link,
      @JsonProperty("id") int id) {
    this.author = author;
    this.publicationDate = publicationDate;
    this.name = name;
    this.link = link;
    this.id = id;
  }
```

* 생성자 위에   `@JsonCreator(mode = Mode.PROPERTIES)` 를 붙임

* 생성자 파라미터마다  `@JsonProperty("필드명")`을 정해주면 잘 되었음.

* lombok 환경에서 아래와 같은 식으로 써줘도 된다고는 봤는데, 나는 잘 되지 않았다.

  ```java
  @RequiredArgsConstructor(onConstructor=@__({@JsonCreator(mode = Mode.PROPERTIES)}))
  ...
  @RequiredArgsConstructor(onConstructor_={@JsonCreator(mode = Mode.PROPERTIES)}) 
  public class TournamentContent {
      ...
  }
  ```

  * 💡 이부분은 lombok을 쓰지 않고 처음처럼 직접 쓰는게 나을 것 같다. 
    

## 정오표

* ...

