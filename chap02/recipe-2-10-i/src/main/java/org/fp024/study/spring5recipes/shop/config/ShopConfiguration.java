package org.fp024.study.spring5recipes.shop.config;

import org.fp024.study.spring5recipes.shop.Product;
import org.fp024.study.spring5recipes.shop.ProductCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.fp024.study.spring5recipes.shop")
public class ShopConfiguration {
  @Bean
  public Product aaa() {
    return ProductCreator.createProduct("aaa");
  }

  @Bean
  public Product cdrw() {
    return ProductCreator.createProduct("cdrw");
  }

  @Bean
  public Product dvdrw() {
    return ProductCreator.createProduct("dvdrw");
  }
}
