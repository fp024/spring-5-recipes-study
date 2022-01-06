package org.fp024.study.spring5recipes.shop.config;

import java.util.HashMap;
import java.util.Map;
import org.fp024.study.spring5recipes.shop.Battery;
import org.fp024.study.spring5recipes.shop.Disc;
import org.fp024.study.spring5recipes.shop.Product;
import org.fp024.study.spring5recipes.shop.ProductCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.fp024.study.spring5recipes.shop")
public class ShopConfiguration {
  @Bean
  public ProductCreator productCreatorFactory() {
    ProductCreator factory = new ProductCreator();
    Map<String, Product> products = new HashMap<>();
    products.put("aaa", new Battery("AAA", 2.5));
    products.put("cdrw", new Disc("CD-RW", 1.5));
    products.put("dvdrw", new Disc("DVD-RW", 3.0));
    factory.setProducts(products);
    return factory;
  }

  @Bean
  public Product aaa() {
    return productCreatorFactory().createProduct("aaa");
  }

  @Bean
  public Product cdrw() {
    return productCreatorFactory().createProduct("cdrw");
  }

  @Bean
  public Product dvdrw() {
    return productCreatorFactory().createProduct("dvdrw");
  }
}
