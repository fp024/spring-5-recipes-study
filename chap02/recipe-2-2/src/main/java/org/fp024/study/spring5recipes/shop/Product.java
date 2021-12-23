package org.fp024.study.spring5recipes.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  private String name;
  private double price;

  @Override
  public String toString() {
    return name + " " + price;
  }
}
