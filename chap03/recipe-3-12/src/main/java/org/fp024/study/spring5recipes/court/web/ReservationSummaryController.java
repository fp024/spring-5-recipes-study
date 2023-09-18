package org.fp024.study.spring5recipes.court.web;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.court.domain.Reservation;
import org.fp024.study.spring5recipes.court.service.ReservationService;
import org.fp024.study.spring5recipes.court.web.exception.NotSupportExtensionException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class ReservationSummaryController {

  private final ReservationService reservationService;

  @GetMapping("/reservationSummary{ext}")
  public String generateSummary(
      @RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate selectedDate,
      @PathVariable("ext") String ext,
      Model model) {

    List<Reservation> reservations = reservationService.findByDate(selectedDate);

    model.addAttribute("reservations", reservations);
    if (".pdf".equals(ext)) {
      return "pdfReservationSummary";
    } else if (".xlsx".equals(ext)) {
      return "excelReservationSummary";
    } else if ("".equals(ext)) {
      return "reservationSummary";
    } else {
      throw new NotSupportExtensionException(ext);
    }
  }

  @ExceptionHandler
  public String notSupportExtension(NotSupportExtensionException e, Model model) {
    model.addAttribute("exception", e);
    return "reservationSummaryError";
  }

  @ExceptionHandler
  public String dateTimeParseException(DateTimeParseException e, Model model) {
    model.addAttribute("exception", e);
    return "reservationSummaryError";
  }
}
