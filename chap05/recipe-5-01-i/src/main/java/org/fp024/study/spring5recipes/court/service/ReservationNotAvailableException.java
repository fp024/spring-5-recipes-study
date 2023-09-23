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

  // 기본 JSTL 에서는 Java 8 부터 추가된 날짜 타입을 처리할 수 없어서 Date로 변환한 로직이 있다.
  public Date getDate() {
    return Date.from(this.date.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }
}
