package org.fp024.study.spring5recipes.bookshop;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.bookshop.config.BookstoreConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Main {
  public static void main(String[] args) {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(BookstoreConfiguration.class)) {

      Cashier cashier = context.getBean(Cashier.class);
      List<String> isbnList = Arrays.asList("0001", "0002");

      try {
        cashier.checkout(isbnList, "user1");
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
  }
}
