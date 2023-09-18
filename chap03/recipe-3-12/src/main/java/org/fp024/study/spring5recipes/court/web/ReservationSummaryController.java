package org.fp024.study.spring5recipes.court.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.court.domain.Reservation;
import org.fp024.study.spring5recipes.court.service.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/reservationSummary*")
public class ReservationSummaryController {

  private final ReservationService reservationService;

  @GetMapping
  public String generateSummary(
      @RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate selectedDate,
      Model model) {

    List<Reservation> reservations = reservationService.findByDate(selectedDate);

    model.addAttribute("reservations", reservations);

    return "reservationSummary";
  }

  /*
    나는 `selectedDate`를 LocalDate로 그대로 받아서 문제가 있다면 ParseException말고 다른 예외가 발생할 것 같은데...
  */
  @ExceptionHandler
  public void handle(ServletRequestBindingException ex, @RequestParam(value = "date") String date) {
    if (ex.getRootCause() instanceof ParseException) {
      // 아래 부터 3줄은 예외 상세 스택을 비우기 위해 설정한 것 같다.
      // 그냥 getStack()을 오버라이드해서 빈문자열만 반환하게 해도 될 것 같은데...
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      ex.getRootCause().printStackTrace(pw);
      throw new ReservationWebException(
          "Invalid date (%s) format for reservation summary".formatted(date),
          new Date(),
          sw.toString()) {
        @Override
        public String getStack() {
          return "";
        }
      };
    }
  }
}
