package org.fp024.study.spring5recipes.shop;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class Disc extends Product {
  private int capacity;

  public Disc() {
    super();    
  }

  public Disc(String name, double price) {
    super(name, price);
  }  
}
