package org.fp024.study.spring5recipes.bank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import org.fp024.study.spring5recipes.bank.config.BankConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = BankConfiguration.class)
class AccountServiceJUnitContextTests {

  private static final String TEST_ACCOUNT_NO = "1234";

  @Autowired private AccountService accountService;

  @BeforeEach
  public void init() {
    accountService.createAccount(TEST_ACCOUNT_NO);
    accountService.deposit(TEST_ACCOUNT_NO, 100);
  }

  @Test
  void deposit() {
    accountService.deposit(TEST_ACCOUNT_NO, 50);
    assertThat(accountService.getBalance(TEST_ACCOUNT_NO)) //
        .isCloseTo(150, within(0.0));
  }

  @Test
  void withDraw() {
    accountService.withdraw(TEST_ACCOUNT_NO, 50);
    assertThat(accountService.getBalance(TEST_ACCOUNT_NO)) //
        .isCloseTo(50, within(0.0));
  }

  @AfterEach
  void cleanup() {
    accountService.removeAccount(TEST_ACCOUNT_NO);
  }
}
