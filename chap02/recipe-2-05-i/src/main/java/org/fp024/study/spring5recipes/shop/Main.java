package org.fp024.study.spring5recipes.shop;

import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.shop.config.ShopConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Main {
  public static void main(String[] args) {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(ShopConfiguration.class)) {

      Product aaa = context.getBean("aaa", Product.class);
      Product cdrw = context.getBean("cdrw", Product.class);
      Product dvdrw = context.getBean("dvdrw", Product.class);

      ShoppingCart cart1 = context.getBean("shoppingCart", ShoppingCart.class);
      cart1.addItem(aaa);
      cart1.addItem(cdrw);
      LOGGER.info("Shopping cart 1 contains {}", cart1.getItems());

      ShoppingCart cart2 = context.getBean("shoppingCart", ShoppingCart.class);
      cart2.addItem(dvdrw);
      LOGGER.info("Shopping cart 2 contains {}", cart2.getItems());
    }
  }
}
