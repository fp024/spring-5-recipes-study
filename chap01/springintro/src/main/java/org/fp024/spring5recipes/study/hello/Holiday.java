package org.fp024.spring5recipes.study.hello;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Holiday {
  private int month;
  private int day;
  private String greeting;
}
