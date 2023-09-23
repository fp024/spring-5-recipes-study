package org.fp024.study.spring5recipes.court.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.court.domain.Reservation;
import org.fp024.study.spring5recipes.court.service.ReservationService;
import org.fp024.study.spring5recipes.court.util.SleepUtil;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/reservationQuery")
public class ReservationQueryController {

  private final ReservationService reservationService;
  private final AsyncListenableTaskExecutor taskExecutor;

  @GetMapping
  public void setupForm() {}

  @PostMapping
  public ListenableFuture<String> submitForm(
      @RequestParam("courtName") String courtName, Model model) {

    return taskExecutor.submitListenable(
        () -> {
          List<Reservation> reservations = java.util.Collections.emptyList();

          if (courtName != null) {
            SleepUtil.delay(3000);
            reservations = reservationService.query(courtName);
          }
          model.addAttribute("reservations", reservations);
          return "reservationQuery";
        });
  }
}
