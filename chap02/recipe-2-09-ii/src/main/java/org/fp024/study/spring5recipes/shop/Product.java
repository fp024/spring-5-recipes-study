package org.fp024.study.spring5recipes.shop;

import javax.annotation.PostConstruct;
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
    LOGGER.info("제품 객체 생성 - 이름 지정 없음");
  }

  public Product(String name, double price) {
    this.name = name;
    this.price = price;
    LOGGER.info("{} 제품 객체 생성", name);
  }

  @PostConstruct
  private void postConstruct() {
    LOGGER.info("{} 객체의 PostConstruct 호출", name);
  }
}
