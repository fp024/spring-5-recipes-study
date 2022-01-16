package org.fp024.study.spring5recipes.bookshop;

import org.fp024.study.spring5recipes.bookshop.config.BookstoreConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
  public static void main(String[] args) {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(BookstoreConfiguration.class)) {

      final BookShop bookShop = context.getBean(BookShop.class);

      final String isbn = "0001";

      System.out.println("before stock :" + bookShop.getStock(isbn));
      try {
        bookShop.purchase(isbn, "user01");
      } catch (Exception e) {
        e.printStackTrace();
      }
      System.out.println("after stock :" + bookShop.getStock(isbn));
    }
  }
}
