package org.fp024.study.spring5recipes.reactive.court.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SportType {
  private int id;
  private String name;

  public SportType(int id, String name) {
    this.id = id;
    this.name = name;
  }
}
