package org.fp024.study.spring5recipes.bookshop;

import java.util.List;

public interface Cashier {
  void checkout(List<String> isbns, String username);
}
