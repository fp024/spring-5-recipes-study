package org.fp024.study.spring5recipes.court.mockserver;

import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
  💡 main 경로의 TournamentContent를 그대로 쓸 수 없다.
     Jackson으로 역직열화를 하려면 기본 생성자가 필요함.
     차라리 클라이언트 측에서는 읽기 전용 도메인을 만드는게 나을 수 있음.
*/
@Getter
@Setter
@ToString
public class TournamentContents {
  private List<TournamentContent> feedContent;

  @Getter
  @Setter
  @ToString
  public static class TournamentContent {
    private String author;
    private Date publicationDate;
    private String name;
    private String link;
    private int id;
  }
}
