package org.fp024.study.spring5recipes.bank.dao;

import org.fp024.study.spring5recipes.bank.domain.Account;

public interface AccountDao {

  void createAccount(Account account);

  void updateAccount(Account account);

  void removeAccount(Account account);

  Account findAccount(String accountNo);
}
