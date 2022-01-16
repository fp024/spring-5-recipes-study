package org.fp024.study.spring5recipes.bookshop;

import java.util.List;
import lombok.Setter;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class BookShopCashier implements Cashier {
  @Setter private BookShop bookShop;

  @Transactional(propagation = Propagation.REQUIRED)
  public void checkout(List<String> isbns, String username) {
    for (String isbn : isbns) {
      bookShop.purchase(isbn, username);
    }
  }
}
