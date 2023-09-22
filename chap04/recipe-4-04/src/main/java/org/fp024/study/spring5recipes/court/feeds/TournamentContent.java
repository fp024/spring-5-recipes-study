package org.fp024.study.spring5recipes.court.feeds;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TournamentContent {

  private static final AtomicInteger idCounter = new AtomicInteger();

  private final String author;

  private final Date publicationDate;

  private final String name;

  private final String link;

  private final int id;

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

  public static TournamentContent of(String author, Date date, String name, String link) {
    return new TournamentContent(author, date, name, link, idCounter.incrementAndGet());
  }
}
