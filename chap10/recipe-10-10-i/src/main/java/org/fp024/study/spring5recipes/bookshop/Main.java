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
      List<String> isbnList = Arrays.asList("0001", "0002");
      cashier.checkout(isbnList, "user1");

    } catch (DataIntegrityViolationException e) {
      // user1의 가진돈이 20원인데, 30원 책으로 체크아웃하려해서
      // POSITIVE_BALANCE 제약조건에 걸려 실패한다.
      LOGGER.error(e.getMessage(), e);
    }
  }
}
