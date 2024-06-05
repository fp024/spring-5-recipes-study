package org.fp024.study.spring5recipes.bank.dao;

import org.fp024.study.spring5recipes.bank.domain.Account;
import org.fp024.study.spring5recipes.bank.exception.AccountNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcAccountDao implements AccountDao {

  private final JdbcTemplate jdbcTemplate;

  public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void createAccount(Account account) {
    String sql = "INSERT INTO ACCOUNT (ACCOUNT_NO, BALANCE) VALUES (?, ?)";
    jdbcTemplate.update(sql, account.getAccountNo(), account.getBalance());
  }

  public void updateAccount(Account account) {
    String sql = "UPDATE ACCOUNT SET BALANCE = ? WHERE ACCOUNT_NO = ?";
    jdbcTemplate.update(sql, account.getBalance(), account.getAccountNo());
  }

  public void removeAccount(Account account) {
    String sql = "DELETE FROM ACCOUNT WHERE ACCOUNT_NO = ?";
    jdbcTemplate.update(sql, account.getAccountNo());
  }

  public Account findAccount(String accountNo) {
    String sql = "SELECT BALANCE FROM ACCOUNT WHERE ACCOUNT_NO = ?";
    Double balance = jdbcTemplate.queryForObject(sql, Double.class, accountNo);
    if (balance == null) {
      throw new AccountNotFoundException();
    }
    return new Account(accountNo, balance);
  }
}
