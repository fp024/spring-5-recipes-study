package org.fp024.study.spring5recipes.reactive.court.exception;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReservationNotAvailableException extends RuntimeException {
  public static final long serialVersionUID = 1L;
  @Getter private final String courtName;
  private final LocalDate date;
  @Getter private final int hour;

  public Date getDate() {
    return Date.from(this.date.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }
}
