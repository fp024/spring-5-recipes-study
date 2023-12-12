package org.fp024.study.spring5recipes.board.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Todo {
  private Long id;

  private String owner;

  @NotEmpty private String description;

  private boolean completed = false;
}
