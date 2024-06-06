package org.fp024.study.spring5recipes.bank.service;

import org.fp024.study.spring5recipes.bank.dao.AccountDao;
import org.fp024.study.spring5recipes.bank.domain.Account;
import org.fp024.study.spring5recipes.bank.exception.InsufficientBalanceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

  private final AccountDao accountDao;

  public AccountServiceImpl(AccountDao accountDao) {
    this.accountDao = accountDao;
  }

  public void createAccount(String accountNo) {
    accountDao.createAccount(new Account(accountNo, 0));
  }

  public void removeAccount(String accountNo) {
    Account account = accountDao.findAccount(accountNo);
    accountDao.removeAccount(account);
  }

  public void deposit(String accountNo, double amount) {
    Account account = accountDao.findAccount(accountNo);
    account.setBalance(account.getBalance() + amount);
    accountDao.updateAccount(account);
  }

  public void withdraw(String accountNo, double amount) {
    Account account = accountDao.findAccount(accountNo);
    if (account.getBalance() < amount) {
      throw new InsufficientBalanceException();
    }
    account.setBalance(account.getBalance() - amount);
    accountDao.updateAccount(account);
  }

  public double getBalance(String accountNo) {
    return accountDao.findAccount(accountNo).getBalance();
  }
}
