package org.fp024.study.spring5recipes.shop.config;

import org.fp024.study.spring5recipes.shop.Battery;
import org.fp024.study.spring5recipes.shop.Disc;
import org.fp024.study.spring5recipes.shop.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShopConfiguration {
  @Bean
  public Product aaa() {
    Battery p1 = new Battery("AAA", 2.5);
    p1.setRechargeable(true);
    return p1;
  }

  @Bean
  public Product cdrw() {
    Disc p2 = new Disc("CD-RW", 1.5);
    p2.setCapacity(700);
    return p2;
  }
}
