package org.fp024.study.spring5recipes.reactive.court.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
  @GetMapping({"/", "/index"})
  public String index() {
    return "index";
  }
}
