package org.fp024.study.spring5recipes.reactive.court.domain;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Reservation {

  private String courtName;

  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate date;

  private int hour;

  private Player player;

  private SportType sportType;

  public Reservation(
      String courtName, LocalDate date, int hour, Player player, SportType sportType) {
    this.courtName = courtName;
    this.date = date;
    this.hour = hour;
    this.player = player;
    this.sportType = sportType;
  }
}
