package org.fp024.study.spring5recipes.shop;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CheckoutEvent {
  private final ShoppingCart cart;
  private final LocalDateTime time;

  public CheckoutEvent(ShoppingCart cart, LocalDateTime time) {
    this.cart = cart;
    this.time = time;
  }
}
