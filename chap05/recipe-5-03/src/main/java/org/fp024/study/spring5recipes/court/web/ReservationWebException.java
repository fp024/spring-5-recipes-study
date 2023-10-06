package org.fp024.study.spring5recipes.court.web;

import java.io.Serial;
import java.util.Date;
import lombok.Getter;

@Getter
public class ReservationWebException extends RuntimeException {

  @Serial public static final long serialVersionUID = 1L;
  private final String message;
  private final String stack;
  private final Date date;

  public ReservationWebException(String message, Date date, String stack) {
    this.message = message;
    this.date = date;
    this.stack = stack;
  }
}
