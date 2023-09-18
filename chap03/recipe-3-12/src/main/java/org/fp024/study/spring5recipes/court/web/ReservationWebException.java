package org.fp024.study.spring5recipes.court.web;

import java.util.Date;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReservationWebException extends RuntimeException {

  public static final long serialVersionUID = 1L;
  private final String message;
  private final Date date;
  private final String stack;
}
