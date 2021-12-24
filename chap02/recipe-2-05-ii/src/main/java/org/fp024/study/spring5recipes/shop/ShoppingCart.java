package org.fp024.study.spring5recipes.shop;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ShoppingCart {
  @Getter private List<Product> items = new ArrayList<>();

  public void addItem(Product item) {
    items.add(item);
  }
}
