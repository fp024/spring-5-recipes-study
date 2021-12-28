package org.fp024.study.spring5recipes.shop;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@ToString
public abstract class Product {
  private String name;
  private double price;
  private double discount;

  public Product() {
    LOGGER.info("{}의 인자 없는 생성자 호출됨", getClass().getSimpleName());
  }

  public Product(String name, double price) {
    LOGGER.info("{}의 인자 두개 생성자 호출됨", getClass().getSimpleName());
    this.name = name;
    this.price = price;
  }
}
