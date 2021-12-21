package org.fp024.spring5recipes.study.hello;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Holiday {
  private int month;
  private int day;
  private String greeting;
}
