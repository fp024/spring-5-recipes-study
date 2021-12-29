package org.fp024.study.spring5recipes.shop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class Battery extends Product {

  @Getter @Setter private boolean rechargeable;

  public Battery(String name, double price) {
    super(name, price);
  }
}
