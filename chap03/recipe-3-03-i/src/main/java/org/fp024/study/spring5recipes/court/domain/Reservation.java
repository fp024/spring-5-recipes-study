package org.fp024.study.spring5recipes.court.domain;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

  private String courtName;

  private LocalDate date;

  private int hour;

  private Player player;

  private SportType sportType;
}
