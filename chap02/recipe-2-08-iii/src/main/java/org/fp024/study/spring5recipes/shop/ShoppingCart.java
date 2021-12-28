package org.fp024.study.spring5recipes.shop;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope("prototype")
@Lazy
public class ShoppingCart {
  @Getter private List<Product> items = new ArrayList<>();

  public ShoppingCart() {
    LOGGER.info("@Lazy 어노테이션이 붙은 ShoppingCart의 생성자가 호출됨");
  }

  public void addItem(Product item) {
    items.add(item);
  }
}
