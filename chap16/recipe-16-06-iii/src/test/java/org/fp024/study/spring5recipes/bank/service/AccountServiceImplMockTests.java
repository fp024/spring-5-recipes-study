package org.fp024.study.spring5recipes.bank.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.fp024.study.spring5recipes.bank.dao.AccountDao;
import org.fp024.study.spring5recipes.bank.domain.Account;
import org.fp024.study.spring5recipes.bank.exception.InsufficientBalanceException;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MockitoTestNGListener.class)
class AccountServiceImplMockTests {
  private static final String TEST_ACCOUNT_NO = "1234";

  @Mock private AccountDao accountDao;
  private AccountService accountService;

  @BeforeMethod
  public void init() {
    accountService = new AccountServiceImpl(accountDao);
  }

  @Test
  void deposit() {
    // Setup
    Account account = new Account(TEST_ACCOUNT_NO, 100);
    when(accountDao.findAccount(TEST_ACCOUNT_NO)).thenReturn(account);

    // Execute
    accountService.deposit(TEST_ACCOUNT_NO, 50);

    // Verify
    verify(accountDao, times(1)).findAccount(any(String.class));
    verify(accountDao, times(1)).updateAccount(account);
  }

  @Test
  void withdrawWithSufficientBalance() {
    // Setup
    Account account = new Account(TEST_ACCOUNT_NO, 100);
    when(accountDao.findAccount(TEST_ACCOUNT_NO)).thenReturn(account);

    // Execute
    accountService.withdraw(TEST_ACCOUNT_NO, 50);

    // Verify
    verify(accountDao, times(1)).findAccount(any(String.class));
    verify(accountDao, times(1)).updateAccount(account);
  }

  @Test
  void testWithdrawWithInsufficientBalance() {
    // Setup
    Account account = new Account(TEST_ACCOUNT_NO, 100);
    when(accountDao.findAccount(TEST_ACCOUNT_NO)).thenReturn(account);

    // Execute
    assertThatThrownBy(() -> accountService.withdraw(TEST_ACCOUNT_NO, 150))
        .isInstanceOf(InsufficientBalanceException.class);
  }
}
