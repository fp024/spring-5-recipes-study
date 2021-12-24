package org.fp024.study.spring5recipes.shop;

import java.text.DecimalFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Product {
  private String name;
  private double price;
  private double discount;

  public String toString() {
    DecimalFormat df = new DecimalFormat("#%");
    return name + " " + price + " (" + df.format(discount) + " discount!)";
  }
}
