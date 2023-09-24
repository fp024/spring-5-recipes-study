package org.fp024.study.spring5recipes.court.web;

import java.util.List;
import java.util.concurrent.Callable;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.court.domain.Player;
import org.fp024.study.spring5recipes.court.domain.Reservation;
import org.fp024.study.spring5recipes.court.domain.SportType;
import org.fp024.study.spring5recipes.court.service.ReservationService;
import org.fp024.study.spring5recipes.court.util.SleepUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequiredArgsConstructor
@SessionAttributes("reservation")
public class ReservationFormController {
  private final ReservationService reservationService;

  @ModelAttribute("sportTypes")
  public List<SportType> populateSportTypes() {
    return reservationService.getAllSportTypes();
  }

  @GetMapping("/reservationForm")
  public String setupForm(
      @RequestParam(required = false, value = "username") String username, Model model) {

    Reservation reservation = new Reservation();
    reservation.setPlayer(new Player(username, null));
    model.addAttribute("reservation", reservation);
    return "reservationForm";
  }

  @PostMapping("/reservationForm")
  public Callable<String> submitForm(
      @ModelAttribute("reservation") @Valid Reservation reservation,
      BindingResult result,
      SessionStatus status) {
    return () -> {
      if (result.hasErrors()) {
        return "reservationForm";
      } else {
        SleepUtil.randomDelay();
        reservationService.make(reservation);
        status.setComplete();
        return "redirect:reservationSuccess";
      }
    };
  }

  @GetMapping("reservationSuccess")
  public void reservationSuccess() {}
}
