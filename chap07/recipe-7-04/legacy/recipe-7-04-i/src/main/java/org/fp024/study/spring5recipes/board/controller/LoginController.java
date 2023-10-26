package org.fp024.study.spring5recipes.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

  @GetMapping("/login")
  public void login() {}

  @GetMapping("/logout-success")
  public void logoutSuccess() {}
}
