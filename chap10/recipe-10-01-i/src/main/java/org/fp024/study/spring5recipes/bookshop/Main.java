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

      // 책 구매시 재고 소진 후, 사용자 잔액을 차감하는데, 차감부분에서 오류가 났을때
      // 재고소진 롤백 처리가 안되어있어, 재고는 그대로 빠져있다.
      LOGGER.info("before stock: {}, after stock: {}", beforeStock, afterStock);
    }
  }
}
