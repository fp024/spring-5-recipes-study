package org.fp024.study.spring5recipes.court.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import lombok.Getter;

public class ReservationNotAvailableException extends RuntimeException {

  public static final long serialVersionUID = 1L;
  @Getter private final String courtName;
  private final LocalDate date;

  @Getter private final int hour;

  public ReservationNotAvailableException(String courtName, LocalDate date, int hour) {
    this.courtName = courtName;
    this.date = date;
    this.hour = hour;
  }

  public Date getDate() {
    return Date.from(this.date.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }
}
