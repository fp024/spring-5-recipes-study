package org.fp024.study.spring5recipes.bookshop;

import java.io.IOException;

public interface BookShop {
  void purchase(String isbn, String username) throws IOException;

  int getStock(String isbn);

  void increaseStock(String isbn, int stock);

  int checkStock(String isbn);
}
