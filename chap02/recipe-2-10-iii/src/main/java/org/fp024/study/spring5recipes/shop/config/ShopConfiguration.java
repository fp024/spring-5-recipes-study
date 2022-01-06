package org.fp024.study.spring5recipes.shop.config;

import org.fp024.study.spring5recipes.shop.Battery;
import org.fp024.study.spring5recipes.shop.Disc;
import org.fp024.study.spring5recipes.shop.DiscountFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.fp024.study.spring5recipes.shop")
public class ShopConfiguration {
  @Bean
  public Battery aaa() {
    Battery aaa = new Battery("AAA", 2.5);
    return aaa;
  }

  @Bean
  public Disc cdrw() {
    Disc aaa = new Disc("CD-RW", 1.5);
    return aaa;
  }

  @Bean
  public Disc dvdrw() {
    Disc aaa = new Disc("DVD-RW", 3.0);
    return aaa;
  }

  // aaa, cdrw, dvdrw 빈을 생성할 때, aaa(), cdrw(), dvdrw() 가 먼저 수행된 뒤에
  // 팩토리 클래스의 createInstance() 가 반환한 값으로 Bean을 생성해주나보다.. 설명을 잘~ 못하겠다. ㅠㅠ
  @Bean
  public DiscountFactoryBean discountFactoryBeanAAA() {
    return new DiscountFactoryBean(aaa(), 0.2);
  }

  @Bean
  public DiscountFactoryBean discountFactoryBeanCDRW() {
    return new DiscountFactoryBean(cdrw(), 0.1);
  }

  @Bean
  public DiscountFactoryBean discountFactoryBeanDVDRW() {
    return new DiscountFactoryBean(dvdrw(), 0.1);
  }
}
