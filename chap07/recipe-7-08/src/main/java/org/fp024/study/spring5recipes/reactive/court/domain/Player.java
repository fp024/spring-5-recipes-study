package org.fp024.study.spring5recipes.reactive.court.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Player {
  private String name;
  private String phone;

  public Player(String name, String phone) {
    this.name = name;
    this.phone = phone;
  }
}
