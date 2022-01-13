package org.fp024.study.spring5recipes.shop;

import java.io.IOException;
import java.time.LocalDateTime;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public class Cashier implements ApplicationEventPublisherAware {
  private ApplicationEventPublisher applicationEventPublisher;

  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void checkout(ShoppingCart cart) throws IOException {
    CheckoutEvent event = new CheckoutEvent(cart, LocalDateTime.now());
    applicationEventPublisher.publishEvent(event);
  }
}
