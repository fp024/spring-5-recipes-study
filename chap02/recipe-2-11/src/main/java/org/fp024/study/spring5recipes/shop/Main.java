package org.fp024.study.spring5recipes.shop;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Main {
  public static void main(String[] args) throws Exception {
    try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
      String[] profiles =
          ((String) context.getEnvironment().getSystemProperties().get("spring.active.profiles"))
              .split(",");

      context.getEnvironment().setActiveProfiles(profiles);

      LOGGER.info("active profiles: {}", Arrays.toString(profiles));
      context.scan("org.fp024.study.spring5recipes.shop");
      context.refresh();

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

      Cashier cashier = context.getBean("cashier", Cashier.class);
      cashier.checkout(cart1);
      cashier.checkout(cart2);
    }
  }
}
