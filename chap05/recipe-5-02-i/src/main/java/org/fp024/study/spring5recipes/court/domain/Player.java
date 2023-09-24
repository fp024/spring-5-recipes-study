package org.fp024.study.spring5recipes.court.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player {
  @NotNull
  @NotEmpty(message = "{required.playerName}")
  private String name;

  @NotNull
  @NotEmpty(message = "{required.playerPhone}")
  private String phone;
}
