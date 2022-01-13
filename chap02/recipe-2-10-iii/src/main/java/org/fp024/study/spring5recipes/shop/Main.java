package org.fp024.study.spring5recipes.shop;

import org.fp024.study.spring5recipes.shop.config.ShopConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class Main {
  public static void main(String[] args) throws Exception {
    try (GenericApplicationContext context =
        new AnnotationConfigApplicationContext(ShopConfiguration.class)) {

      Product aaa = context.getBean("aaa", Product.class);
      Product cdrw = context.getBean("cdrw", Product.class);
      Product dvdrw = context.getBean("dvdrw", Product.class);

      ShoppingCart cart1 = context.getBean("shoppingCart", ShoppingCart.class);
      cart1.addItem(aaa);
      cart1.addItem(cdrw);
      System.out.println("Shopping cart 1 contains " + cart1.getItems());

      ShoppingCart cart2 = context.getBean("shoppingCart", ShoppingCart.class);
      cart2.addItem(dvdrw);
      System.out.println("Shopping cart 2 contains " + cart2.getItems());
    }
  }
}