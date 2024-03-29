package org.fp024.study.spring5recipes.shop;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CheckoutListener {

  @EventListener
  public void onApplicationEvent(CheckoutEvent event) {
    // 체크아웃 시 수행할 로직을 여기에 구현합니다.
    System.out.println("Checkout event [" + event.getTime() + "]");
  }
}
