package org.fp024.study.spring5recipes.bank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

import org.fp024.study.spring5recipes.bank.dao.AccountDao;
import org.fp024.study.spring5recipes.bank.domain.Account;
import org.fp024.study.spring5recipes.bank.exception.InsufficientBalanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountServiceImplStubTests {
  private static final String TEST_ACCOUNT_NO = "1234";
  private AccountDaoStub accountDaoStub;
  private AccountService accountService;

  @BeforeEach
  public void init() {
    accountDaoStub = new AccountDaoStub();
    accountDaoStub.accountNo = TEST_ACCOUNT_NO;
    accountDaoStub.balance = 100;
    accountService = new AccountServiceImpl(accountDaoStub);
  }

  @Test
  void deposit() {
    accountService.deposit(TEST_ACCOUNT_NO, 50);
    assertThat(accountDaoStub.accountNo).isEqualTo(TEST_ACCOUNT_NO);

    // assertEquals의 3번째 인자 delta는 오차범위를 말하는데,
    // 0 이면 그냥 assertEquals(x, y)를 쓰는 것과 같다.
    assertThat(accountDaoStub.balance).isCloseTo(150, within(0.0));
  }

  @Test
  void withdrawWithSufficientBalance() {
    accountService.withdraw(TEST_ACCOUNT_NO, 50);
    assertThat(accountDaoStub.accountNo).isEqualTo(TEST_ACCOUNT_NO);
    assertThat(accountDaoStub.balance).isCloseTo(50, within(0.0));
  }

  @Test
  void withdrawWithInsufficientBalance() {
    assertThatThrownBy(() -> accountService.withdraw(TEST_ACCOUNT_NO, 150))
        .isInstanceOf(InsufficientBalanceException.class);
  }

  /** Partially implemented stub implementation for the {@code AccountDao} */
  private static class AccountDaoStub implements AccountDao {

    private String accountNo;
    private double balance;

    public void createAccount(Account account) {}

    public void removeAccount(Account account) {}

    public Account findAccount(String accountNo) {
      return new Account(this.accountNo, this.balance);
    }

    public void updateAccount(Account account) {
      this.accountNo = account.getAccountNo();
      this.balance = account.getBalance();
    }
  }
}
