package org.fp024.study.spring5recipes.court.web;

import java.time.LocalDateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/welcome")
public class WelcomeController {

  @GetMapping
  public String welcome(Model model) {
    LocalDateTime today = LocalDateTime.now();
    model.addAttribute("today", today);
    return "welcome";
  }
}
