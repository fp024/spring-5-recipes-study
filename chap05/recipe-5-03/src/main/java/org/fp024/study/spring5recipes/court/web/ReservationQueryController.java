package org.fp024.study.spring5recipes.court.web;

import java.io.IOException;
import java.util.Collection;
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
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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

  /*
    💡 이번 예제의 주제
  */
  @GetMapping(params = "courtName")
  public SseEmitter find(@RequestParam("courtName") String courtName) {
    final SseEmitter emitter = new SseEmitter();
    taskExecutor.execute(
        () -> {
          Collection<Reservation> reservations = reservationService.query(courtName);
          try {
            for (Reservation reservation : reservations) {
              SleepUtil.delay(125);
              // emitter.send(reservation); // 도메인 단위로 보낸다.
              emitter.send(
                  SseEmitter.event().id(String.valueOf(reservation.hashCode())).data(reservation));
            }
            emitter.complete();
          } catch (IOException e) {
            emitter.completeWithError(e);
          }
        });

    return emitter;
  }
}
