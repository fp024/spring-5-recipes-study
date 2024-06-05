package org.fp024.study.spring5recipes.bank.web;

import org.fp024.study.spring5recipes.bank.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DepositController {

  private final AccountService accountService;

  public DepositController(AccountService accountService) {
    this.accountService = accountService;
  }

  @RequestMapping("/deposit.do")
  protected String deposit(
      @RequestParam("accountNo") String accountNo,
      @RequestParam("amount") double amount,
      ModelMap model) {
    accountService.deposit(accountNo, amount);
    model.addAttribute("accountNo", accountNo);
    model.addAttribute("balance", accountService.getBalance(accountNo));
    return "success";
  }
}
