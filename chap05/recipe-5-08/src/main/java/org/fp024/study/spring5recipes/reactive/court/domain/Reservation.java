package org.fp024.study.spring5recipes.reactive.court.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reservation {

  private String courtName;

  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate date;

  private int hour;
  private Player player;
  private SportType sportType;
}
