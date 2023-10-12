package org.fp024.study.spring5recipes.reactive.court.web;

import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.reactive.court.domain.Player;
import org.fp024.study.spring5recipes.reactive.court.domain.Reservation;
import org.fp024.study.spring5recipes.reactive.court.domain.ReservationValidator;
import org.fp024.study.spring5recipes.reactive.court.domain.SportType;
import org.fp024.study.spring5recipes.reactive.court.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Controller
@RequestMapping("/reservationForm")
public class ReservationFormController {

  private final ReservationService reservationService;
  private final ReservationValidator reservationValidator;

  @ModelAttribute("sportTypes")
  public Flux<SportType> populateSportTypes() {
    return reservationService.getAllSportTypes();
  }

  @GetMapping
  public String setupForm(
      @RequestParam(required = false, value = "username") String username, Model model) {
    Player player = new Player(username, null);
    Reservation reservation = new Reservation();
    reservation.setPlayer(player);
    model.addAttribute("reservation", reservation);
    return "reservationForm";
  }

  @PostMapping
  public String submitForm(
      @Validated @ModelAttribute("reservation") Reservation reservation, BindingResult result) {
    if (result.hasErrors()) {
      return "reservationForm";
    } else {
      reservationService.make(reservation);
      return "redirect:reservationSuccess";
    }
  }

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.setValidator(reservationValidator);
  }
}
