package org.fp024.study.spring5recipes.bank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.bank.config.BankConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Slf4j
@ContextConfiguration(classes = BankConfiguration.class)
@Sql(scripts = {"classpath:/bank.sql"})
@ActiveProfiles("default")
class AccountServiceTestNGContextTests extends AbstractTransactionalTestNGSpringContextTests {

  private static final String TEST_ACCOUNT_NO = "1234";

  @Autowired private AccountService accountService;

  @Autowired private ApplicationContext applicationContext;

  @BeforeMethod
  public void init() {
    accountService.createAccount(TEST_ACCOUNT_NO);
    accountService.deposit(TEST_ACCOUNT_NO, 100);
  }

  @Test
  void deposit() {
    LOGGER.info(
        "### Current Profile: {} ###",
        Arrays.toString(applicationContext.getEnvironment().getActiveProfiles()));
    LOGGER.info("### deposit() ### ");
    accountService.deposit(TEST_ACCOUNT_NO, 50);
    assertThat(accountService.getBalance(TEST_ACCOUNT_NO)) //
        .isCloseTo(150, within(0.0));
  }

  @Test
  void withDraw() {
    LOGGER.info("### withDraw() ### ");
    accountService.withdraw(TEST_ACCOUNT_NO, 50);
    assertThat(accountService.getBalance(TEST_ACCOUNT_NO)) //
        .isCloseTo(50, within(0.0));
  }
}
