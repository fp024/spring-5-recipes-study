package org.fp024.study.spring5recipes.bank.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.fp024.study.spring5recipes.bank.domain.Account;
import org.fp024.study.spring5recipes.bank.exception.AccountNotFoundException;
import org.fp024.study.spring5recipes.bank.exception.DuplicateAccountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryAccountDaoTests {

  private static final String EXISTING_ACCOUNT_NO = "1234";
  private static final String NEW_ACCOUNT_NO = "5678";

  private Account existingAccount;
  private Account newAccount;
  private InMemoryAccountDao accountDao;

  @BeforeEach
  void init() {
    existingAccount = new Account(EXISTING_ACCOUNT_NO, 100);
    newAccount = new Account(NEW_ACCOUNT_NO, 200);
    accountDao = new InMemoryAccountDao();
    accountDao.createAccount(existingAccount);
  }

  @Test
  void accountExists() {
    assertThat(accountDao.accountExists(EXISTING_ACCOUNT_NO)).isTrue();
    assertThat(accountDao.accountExists(NEW_ACCOUNT_NO)).isFalse();
  }

  @Test
  void createNewAccount() {
    accountDao.createAccount(newAccount);
    assertThat(accountDao.findAccount(NEW_ACCOUNT_NO)).isEqualTo(newAccount);
  }

  @Test
  void createDuplicateAccount() {
    assertThatThrownBy(() -> accountDao.createAccount(existingAccount))
        .isInstanceOf(DuplicateAccountException.class);
  }

  @Test
  void updateExistedAccount() {
    existingAccount.setBalance(150);
    accountDao.updateAccount(existingAccount);
    assertThat(accountDao.findAccount(EXISTING_ACCOUNT_NO)).isEqualTo(existingAccount);
  }

  @Test
  void updateNotExistedAccount() {
    assertThatThrownBy(() -> accountDao.updateAccount(newAccount))
        .isInstanceOf(AccountNotFoundException.class);
  }

  @Test
  void removeExistedAccount() {
    accountDao.removeAccount(existingAccount);
    assertThat(accountDao.accountExists(EXISTING_ACCOUNT_NO)).isFalse();
  }

  @Test
  void removeNotExistedAccount() {
    assertThatThrownBy(() -> accountDao.removeAccount(newAccount))
        .isInstanceOf(AccountNotFoundException.class);
  }

  @Test
  void findExistedAccount() {
    Account account = accountDao.findAccount(EXISTING_ACCOUNT_NO);
    assertThat(account).isEqualTo(existingAccount);
  }

  @Test
  void findNotExistedAccount() {
    assertThatThrownBy(() -> accountDao.findAccount(NEW_ACCOUNT_NO))
        .isInstanceOf(AccountNotFoundException.class);
  }
}
