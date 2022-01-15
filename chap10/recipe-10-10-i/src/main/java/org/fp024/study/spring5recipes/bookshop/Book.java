package org.fp024.study.spring5recipes.bookshop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@NoArgsConstructor
public class Book {
  @Getter @Setter private String isbn;

  @Getter @Setter private String name;

  @Getter @Setter private int price;

  private JdbcTemplate jdbcTemplate;

  @Autowired
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Book(String isbn, String name, int price) {
    this.isbn = isbn;
    this.name = name;
    this.price = price;
  }

  @Transactional
  public void purchase(String username) {
    jdbcTemplate.update("UPDATE BOOK_STOCK SET STOCK = STOCK - 1 WHERE ISBN = ?", isbn);

    jdbcTemplate.update(
        "UPDATE ACCOUNT SET BALANCE = BALANCE - ? WHERE USERNAME = ?", price, username);
  }
}
