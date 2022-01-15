package org.fp024.study.spring5recipes.bookshop;

import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.bookshop.config.BookstoreConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Main {
  public static void main(String[] args) {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(BookstoreConfiguration.class)) {

      BookShop bookShop = context.getBean(BookShop.class);

      int beforeStock = bookShop.getStock("0001");

      try {
        bookShop.purchase("0001", "user1");
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }

      int afterStock = bookShop.getStock("0001");

      // 스프링의 트랜젝션 관리자를 사용했다.
      LOGGER.info("before stock: {}, after stock: {}", beforeStock, afterStock);
    }
  }
}
