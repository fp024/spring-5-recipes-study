package org.fp024.study.spring5recipes.court.web;

import java.time.LocalDateTime;
import org.fp024.study.spring5recipes.court.util.SleepUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class WelcomeController {

  @GetMapping("/welcomeRedirect")
  public void welcomeRedirect() {}

  @GetMapping("/welcome")
  public String welcome(Model model) throws InterruptedException {
    LocalDateTime today = LocalDateTime.now();
    model.addAttribute("today", today);

    SleepUtil.sleepMax(500);
    return "welcome";
  }
}
