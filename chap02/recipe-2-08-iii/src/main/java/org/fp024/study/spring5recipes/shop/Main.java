package org.fp024.study.spring5recipes.shop;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.shop.config.ShopConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

@Slf4j
public class Main {
  public static void main(String[] args) throws Exception {
    try (GenericApplicationContext context =
        new AnnotationConfigApplicationContext(ShopConfiguration.class)) {

      if (Arrays.toString(context.getBeanDefinitionNames()).contains("")) {
        LOGGER.info("Lazy라도 shoppingCart 빈이 등록은 되어있다.");
      }

      Product aaa = context.getBean("aaa", Product.class);
      Product cdrw = context.getBean("cdrw", Product.class);
      Product dvdrw = context.getBean("dvdrw", Product.class);

      LOGGER.info("shoppingCart 빈을 불러오기 직전...");
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
