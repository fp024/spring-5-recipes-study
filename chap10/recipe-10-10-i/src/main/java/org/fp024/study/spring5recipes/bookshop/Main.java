package org.fp024.study.spring5recipes.bookshop;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.bookshop.config.BookstoreConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;

@Slf4j
public class Main {
  public static void main(String[] args) {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(BookstoreConfiguration.class)) {

      Cashier cashier = context.getBean(Cashier.class);
      BookShop bookShop = context.getBean(BookShop.class);

      List<String> isbnList = Arrays.asList("0001", "0002");

      final int beforeStock = bookShop.getStockWithNoSleep("0001");

      try {
        cashier.checkout(isbnList, "user1");
      } catch (DataIntegrityViolationException e) {
        // user1의 가진돈이 20원인데, 30원 책으로 체크아웃하려해서
        // POSITIVE_BALANCE 제약조건에 걸려 실패한다.
        LOGGER.error(e.getMessage(), e);
      }

      final int afterStock = bookShop.getStockWithNoSleep("0001");

      // 트랜젝션 기능이 정상 동작했다면 재고 값이 같아야한다.
      LOGGER.info("before stock: {}, after stock: {}", beforeStock, afterStock);
    }
  }
}
