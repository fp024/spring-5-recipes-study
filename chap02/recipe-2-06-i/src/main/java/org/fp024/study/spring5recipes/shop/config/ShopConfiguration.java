package org.fp024.study.spring5recipes.shop.config;

import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.shop.Battery;
import org.fp024.study.spring5recipes.shop.Disc;
import org.fp024.study.spring5recipes.shop.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Slf4j
@Configuration
@PropertySource("classpath:discounts.properties")
@ComponentScan("org.fp024.study.spring5recipes.shop")
public class ShopConfiguration {

  @Value("${specialcustomer.discount:0}")
  private double specialCustomerDiscountField;

  @Value("${summer.discount:0}")
  private double specialSummerDiscountField;

  @Value("${endofyear.discount:0}")
  private double specialEndofyearDiscountField;

  // 아래 빈을 반드시 선언해주지 않아도 잘 되었다. (Spring 5.3.x)
  /*
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }
  */

  @Bean
  public Product aaa() {
    LOGGER.info("specialCustomerDiscountField: {}", specialCustomerDiscountField);
    Battery p1 = new Battery();
    p1.setName("AAA");
    p1.setPrice(2.5);
    p1.setRechargeable(true);
    p1.setDiscount(specialCustomerDiscountField);
    return p1;
  }

  @Bean
  public Product cdrw() {
    LOGGER.info("specialSummerDiscountField: {}", specialSummerDiscountField);
    Disc p2 = new Disc("CD-RW", 1.5, specialSummerDiscountField);
    p2.setCapacity(700);
    return p2;
  }

  @Bean
  public Product dvdrw() {
    LOGGER.info("specialEndofyearDiscountField: {}", specialEndofyearDiscountField);
    Disc p2 = new Disc("DVD-RW", 3.0, specialEndofyearDiscountField);
    p2.setCapacity(700);
    return p2;
  }
}
