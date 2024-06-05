package org.fp024.study.spring5recipes.bank.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.fp024.study.spring5recipes.bank.domain.Account;
import org.fp024.study.spring5recipes.bank.exception.AccountNotFoundException;
import org.fp024.study.spring5recipes.bank.exception.DuplicateAccountException;

public class InMemoryAccountDao implements AccountDao {

  private Map<String, Account> accounts;

  public InMemoryAccountDao() {
    accounts = Collections.synchronizedMap(new HashMap<String, Account>());
  }

  public boolean accountExists(String accountNo) {
    return accounts.containsKey(accountNo);
  }

  public void createAccount(Account account) {
    if (accountExists(account.getAccountNo())) {
      throw new DuplicateAccountException();
    }
    accounts.put(account.getAccountNo(), account);
  }

  public void updateAccount(Account account) {
    if (!accountExists(account.getAccountNo())) {
      throw new AccountNotFoundException();
    }
    accounts.put(account.getAccountNo(), account);
  }

  public void removeAccount(Account account) {
    if (!accountExists(account.getAccountNo())) {
      throw new AccountNotFoundException();
    }
    accounts.remove(account.getAccountNo());
  }

  public Account findAccount(String accountNo) {
    Account account = accounts.get(accountNo);
    if (account == null) {
      throw new AccountNotFoundException();
    }
    return account;
  }
}
