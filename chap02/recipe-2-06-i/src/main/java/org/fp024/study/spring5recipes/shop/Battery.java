package org.fp024.study.spring5recipes.shop;

import lombok.Getter;
import lombok.Setter;

public class Battery extends Product {
  @Getter @Setter private boolean rechargeable;

  public Battery() {
    super();
  }

  public Battery(String name, double price, double discount) {
    super(name, price, discount);
  }
}
