package org.fp024.study.spring5recipes.shop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public abstract class Product {
  private String name;
  private double price;

  public Product(String name, double price) {
    this.name = name;
    this.price = price;
  }
}
