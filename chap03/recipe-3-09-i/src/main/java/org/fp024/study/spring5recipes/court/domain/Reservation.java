package org.fp024.study.spring5recipes.court.domain;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

  private String courtName;

  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate date;

  private int hour;

  private Player player;

  private SportType sportType;
}
