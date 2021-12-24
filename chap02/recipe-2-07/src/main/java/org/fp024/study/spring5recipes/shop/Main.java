package org.fp024.study.spring5recipes.shop;

import java.time.LocalDateTime;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.shop.config.ShopConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

@Slf4j
public class Main {
  public static void main(String[] args) throws Exception {
    try (GenericApplicationContext context =
        new AnnotationConfigApplicationContext(ShopConfiguration.class)) {

      String alert = context.getMessage("alert.checkout", null, Locale.US);
      String alert_inventory =
          context.getMessage(
              "alert.inventory.checkout",
              new Object[] {"[DVD-RW 3.0]", LocalDateTime.now()},
              Locale.US);

      LOGGER.info("The I18N message for alert.checkout is: {}", alert);
      LOGGER.info("The I18N message for alert.inventory.checkout is: {}", alert_inventory);
    }
  }
}
