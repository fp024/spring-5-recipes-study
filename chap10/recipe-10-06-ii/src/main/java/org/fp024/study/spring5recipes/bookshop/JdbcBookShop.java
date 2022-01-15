package org.fp024.study.spring5recipes.bookshop;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;

public class JdbcBookShop extends JdbcDaoSupport implements BookShop {
  @Transactional
  @Override
  public void purchase(String isbn, String username) {
    int price =
        getJdbcTemplate()
            .queryForObject("SELECT PRICE FROM BOOK WHERE ISBN = ?", Integer.class, isbn);

    getJdbcTemplate().update("UPDATE BOOK_STOCK SET STOCK = STOCK - 1 WHERE ISBN = ?", isbn);

    getJdbcTemplate()
        .update("UPDATE ACCOUNT SET BALANCE = BALANCE - ? WHERE USERNAME = ?", price, username);
  }

  @Override
  public int getStock(String isbn) {
    return getJdbcTemplate()
        .queryForObject("SELECT STOCK FROM BOOK_STOCK WHERE ISBN = ?", Integer.class, isbn);
  }
}
