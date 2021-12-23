package org.fp024.study.spring5recipes.shop;

import org.fp024.study.spring5recipes.shop.config.ShopConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
  public static void main(String[] args) {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(ShopConfiguration.class)) {
      Product aaa = context.getBean("aaa", Product.class);
      Product cdrw = context.getBean("cdrw", Product.class);

      LOGGER.info("aaa: {}", aaa);
      LOGGER.info("cdrw: {}", cdrw);
    }
  }
}
