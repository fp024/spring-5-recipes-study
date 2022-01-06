package org.fp024.study.spring5recipes.shop;

import lombok.Getter;
import lombok.Setter;

public class Disc extends Product {
  @Getter @Setter private int capacity;

  public Disc(String name, double price) {
    super(name, price);
  }
}
