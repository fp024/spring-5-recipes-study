package org.fp024.study.spring5recipes.shop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public abstract class Product {
  private String name;
  private double price;
  private double discount;

  public Product(String name, double price) {
    this.name = name;
    this.price = price;
  }
}
