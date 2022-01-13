package org.fp024.study.spring5recipes.shop;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CheckoutEvent extends ApplicationEvent {
  private final ShoppingCart cart;
  private final LocalDateTime time;

  public CheckoutEvent(ShoppingCart cart, LocalDateTime time) {
    super(cart);
    this.cart = cart;
    this.time = time;
  }
}
