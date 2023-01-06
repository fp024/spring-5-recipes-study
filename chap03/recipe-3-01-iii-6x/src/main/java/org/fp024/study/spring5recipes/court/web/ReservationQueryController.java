package org.fp024.study.spring5recipes.court.web;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.court.domain.Reservation;
import org.fp024.study.spring5recipes.court.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/reservationQuery")
public class ReservationQueryController {

  private final ReservationService reservationService;

  @GetMapping
  public void setupForm() {}

  @PostMapping
  public String sumbitForm(@RequestParam("courtName") String courtName, Model model) {

    List<Reservation> reservations = Collections.emptyList();

    if (courtName != null) {
      reservations = reservationService.query(courtName);
    }

    model.addAttribute("reservations", reservations);

    return "reservationQuery";
  }
}
