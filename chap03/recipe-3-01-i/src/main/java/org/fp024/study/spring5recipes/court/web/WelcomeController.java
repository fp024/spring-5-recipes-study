package org.fp024.study.spring5recipes.court.web;

import java.time.LocalDateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/welcome")
public class WelcomeController {

  @RequestMapping(method = RequestMethod.GET)
  public String welcome2(Model model) {
    LocalDateTime today = LocalDateTime.now();
    model.addAttribute("today", today);

    return "welcome";
  }
}
