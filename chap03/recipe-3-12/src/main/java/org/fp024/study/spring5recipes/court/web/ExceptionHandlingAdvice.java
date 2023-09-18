package org.fp024.study.spring5recipes.court.web;

import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.court.service.ReservationNotAvailableException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingAdvice {

  @ExceptionHandler(ReservationNotAvailableException.class)
  public String handle(ReservationNotAvailableException e, Model model) {
    model.addAttribute("exception", e);
    return "reservationNotAvailable";
  }

  @ExceptionHandler
  public String handleDefault(Exception e) {
    LOGGER.error(e.getMessage(), e);
    return "error";
  }
}
