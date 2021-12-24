package org.fp024.study.spring5recipes.shop;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCart {
  @Getter private List<Product> items = new ArrayList<>();

  public void addItem(Product item) {
    items.add(item);
  }
}
