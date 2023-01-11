package org.fp024.study.spring5recipes.court.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.court.domain.Reservation;
import org.fp024.study.spring5recipes.court.service.ReservationService;
import org.fp024.study.spring5recipes.court.util.SleepUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class ReservationQueryController {

  private final ReservationService reservationService;

  @GetMapping("/reservationQuery")
  public void setupForm() {
    SleepUtil.sleepMax(500);
  }

  @PostMapping("/reservationQuery")
  public String sumbitForm(@RequestParam("courtName") String courtName, Model model) {

    List<Reservation> reservations = Collections.emptyList();

    if (courtName != null) {
      reservations = reservationService.query(courtName);
    }

    model.addAttribute("reservations", reservations);
    SleepUtil.sleepMax(500);
    return "reservationQuery";
  }

  /**
   * 예약 요약 파일 다운로드
   * https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html#mvc-ann-requestmapping-uri-templates-regex
   * 정규식 가이드가 있긴 한데, 어떻게 쓰는지 아직 잘 모르겠다. 일단 URL을 다 나눠서 기입
   *
   * @param date yyyy-MM-dd 형식 스트링
   * @param model model
   */
  @GetMapping({"/reservationSummary.html", "/reservationSummary.pdf", "/reservationSummary.xls"})
  public void reservationSummary(@RequestParam("date") String date, Model model) {
    model.addAttribute(
        "reservations",
        reservationService.findByDate(
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
  }
}
