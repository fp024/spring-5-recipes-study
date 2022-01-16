package org.fp024.study.spring5recipes.bookshop;

import org.fp024.study.spring5recipes.bookshop.config.BookstoreConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
  public static void main(String[] args) {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(BookstoreConfiguration.class)) {

      final BookShop bookShop = context.getBean(BookShop.class);

      Thread thread1 =
          new Thread(
              () -> {
                try {
                  bookShop.increaseStock("0001", 5);
                } catch (RuntimeException e) {
                }
              },
              "Thread 1");

      Thread thread2 = new Thread(() -> bookShop.checkStock("0001"), "Thread 2");

      thread1.start();
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
      }
      thread2.start();
    }
  }
}
