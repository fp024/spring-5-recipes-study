package org.fp024.study.spring5recipes.reactive.court.web;

import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/welcome")
public class WelcomeController {

  @GetMapping
  public String welcome(Model model) {
    model.addAttribute("today", Mono.just(LocalDate.now()));
    return "welcome";
  }
}
