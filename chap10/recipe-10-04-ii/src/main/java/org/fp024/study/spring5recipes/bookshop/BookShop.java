package org.fp024.study.spring5recipes.bookshop;

public interface BookShop {
  void purchase(String isbn, String username);

  int getStock(String isbn);
}
