package org.fp024.study.spring5recipes.shop.config;

import org.fp024.study.spring5recipes.shop.Battery;
import org.fp024.study.spring5recipes.shop.Cashier;
import org.fp024.study.spring5recipes.shop.Disc;
import org.fp024.study.spring5recipes.shop.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.fp024.study.spring5recipes.shop")
public class ShopConfiguration {
  @Bean
  public Product aaa() {
    Battery p1 = new Battery();
    p1.setName("AAA");
    p1.setPrice(2.5);
    p1.setRechargeable(true);
    return p1;
  }

  @Bean
  public Product cdrw() {
    Disc p2 = new Disc("CD-RW", 1.5);
    p2.setCapacity(700);
    return p2;
  }

  @Bean
  public Product dvdrw() {
    Disc p2 = new Disc("DVD-RW", 3.0);
    p2.setCapacity(700);
    return p2;
  }

  @Bean
  public Cashier cashier() {
    return new Cashier();
  }
}
