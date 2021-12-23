package org.fp024.study.spring5recipes.shop;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class Battery extends Product {
  private boolean rechargeable;

  public Battery(String name, double price) {
    super(name, price);
  }
}
