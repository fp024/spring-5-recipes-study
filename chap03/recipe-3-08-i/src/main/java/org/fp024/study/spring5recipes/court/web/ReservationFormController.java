package org.fp024.study.spring5recipes.court.web;

import java.util.List;
import org.fp024.study.spring5recipes.court.domain.Player;
import org.fp024.study.spring5recipes.court.domain.Reservation;
import org.fp024.study.spring5recipes.court.domain.ReservationValidator;
import org.fp024.study.spring5recipes.court.domain.SportType;
import org.fp024.study.spring5recipes.court.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/reservationForm")
@SessionAttributes("reservation")
public class ReservationFormController {
  private final ReservationService reservationService;
  private final ReservationValidator validator;

  public ReservationFormController(
      ReservationService reservationService, ReservationValidator validator) {
    this.reservationService = reservationService;
    this.validator = validator;
  }

  @ModelAttribute("sportTypes")
  public List<SportType> populateSportTypes() {
    return reservationService.getAllSportTypes();
  }

  @GetMapping
  public String setupForm(
      @RequestParam(required = false, value = "username") String username, Model model) {

    Reservation reservation = new Reservation();
    reservation.setPlayer(new Player(username, null));
    model.addAttribute("reservation", reservation);
    return "reservationForm";
  }

  @PostMapping
  public String submitForm(
      @ModelAttribute("reservation") Reservation reservation,
      BindingResult result,
      SessionStatus status) {

    validator.validate(reservation, result);

    if (result.hasErrors()) {
      return "reservationForm";
    } else {
      reservationService.make(reservation);
      status.setComplete();

      return "redirect:reservationSuccess";
    }
  }
}
