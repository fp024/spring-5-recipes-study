package org.fp024.study.spring5recipes.bank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.bank.config.BankConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringJUnitConfig(classes = BankConfiguration.class)
@Transactional
@Sql(scripts = {"classpath:/bank.sql"})
@EnabledIf("#{systemProperties['spring.profiles.active'] != 'in-mem'}")
class AccountServiceJUnitContextTests {

  private static final String TEST_ACCOUNT_NO = "1234";

  @Autowired private AccountService accountService;

  @BeforeEach
  public void init() {
    accountService.createAccount(TEST_ACCOUNT_NO);
    accountService.deposit(TEST_ACCOUNT_NO, 100);
  }

  @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
  @Test
  void deposit() {
    accountService.deposit(TEST_ACCOUNT_NO, 50);
    assertThat(accountService.getBalance(TEST_ACCOUNT_NO)) //
        .isCloseTo(150, within(0.0));
  }

  @RepeatedTest(5)
  void withDraw() {
    LOGGER.info("### withDraw() ###");
    accountService.withdraw(TEST_ACCOUNT_NO, 50);
    assertThat(accountService.getBalance(TEST_ACCOUNT_NO)) //
        .isCloseTo(50, within(0.0));
  }
}
