package org.fp024.study.spring5recipes.court.web;

import static org.fp024.study.spring5recipes.court.util.ControllerUtils.getTargetPage;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.court.domain.PeriodicReservation;
import org.fp024.study.spring5recipes.court.domain.PeriodicReservationValidator;
import org.fp024.study.spring5recipes.court.domain.Player;
import org.fp024.study.spring5recipes.court.service.ReservationService;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@RequiredArgsConstructor
@Controller
@RequestMapping("/periodicReservationForm")
@SessionAttributes("reservation")
public class PeriodicReservationController {
  private final Map<Integer, String> pageForms =
      Map.of(
          0, "reservationCourtForm", //
          1, "reservationTimeForm", //
          2, "reservationPlayerForm");
  private final ReservationService reservationService;
  private final PeriodicReservationValidator validator;

  @GetMapping
  public String setupForm(Model model) {
    PeriodicReservation reservation = new PeriodicReservation();
    reservation.setPlayer(new Player());
    model.addAttribute("reservation", reservation);

    return "reservationCourtForm";
  }

  /*
   파라미터에 `_cancel`이 있을 때만 매핑
  */
  @PostMapping(params = {"_cancel"})
  public String cancelForm(
      @ModelAttribute("reservation") PeriodicReservation reservation,
      @RequestParam("_page") int currentPage) {
    return pageForms.get(currentPage);
  }

  /*
    중간단계의 Submit 처리
  */
  @PostMapping
  public String submitForm(
      HttpServletRequest request,
      @ModelAttribute("reservation") PeriodicReservation reservation,
      BindingResult result,
      @RequestParam("_page") int currentPage) {

    int targetPage = getTargetPage(request, "_target", currentPage);

    // 현재 패이이지 인덱스가 타겟 페이지 인덱스보다 클때: Previous 버튼 누름
    if (targetPage < currentPage) {
      return pageForms.get(targetPage);
    }

    // 현재 페이지에 대한 폼 검증
    validateCurrentPage(reservation, result, currentPage);

    // 에러가 없으면 다음 페이지로 이동
    if (!result.hasErrors()) {
      return pageForms.get(targetPage);
    } else {
      return pageForms.get(currentPage);
    }
  }

  /*
   폼 검증을 페이지 인덱스 번호대로 별도 처리함.
  */
  private void validateCurrentPage(
      PeriodicReservation reservation, BindingResult result, int currentPage) {
    switch (currentPage) {
      case 0:
        validator.validateCourt(reservation, result);
        break;
      case 1:
        validator.validateTime(reservation, result);
        break;
      case 2:
        validator.validatePlayer(reservation, result);
        break;
    }
  }

  /*
   매일과  주당 일회를 구분하기 위한 period 옵션
  */
  @ModelAttribute("periods")
  public Map<Integer, String> periods() {
    return Map.of(
        1, "Daily", //
        7, "Weekly");
  }

  /*
   최종 완료 폼

   그동안 세션에 저장되어 누적된 reservation 내용을 한번에 처리한다.
  */
  @PostMapping(params = {"_finish"})
  public String completeForm(
      @Validated @ModelAttribute("reservation") PeriodicReservation reservation,
      BindingResult result,
      SessionStatus status,
      @RequestParam("_page") int currentPage) {
    if (!result.hasErrors()) {
      reservationService.makePeriodic(reservation);
      status.setComplete();
      return "redirect:reservationSuccess";
    } else {
      return pageForms.get(currentPage);
    }
  }

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.setValidator(this.validator);
  }
}
