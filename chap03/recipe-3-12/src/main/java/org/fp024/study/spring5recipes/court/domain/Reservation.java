package org.fp024.study.spring5recipes.court.domain;

import java.time.LocalDate;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
  @NotNull(message = "{required.courtName}")
  @Size(min = 4, max = 20)
  private String courtName;

  @NotNull(message = "{required.date}")
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate date;

  @Min(9)
  @Max(21)
  private int hour;

  @Valid private Player player;

  @NotNull(message = "{required.sportType}")
  private SportType sportType;
}
