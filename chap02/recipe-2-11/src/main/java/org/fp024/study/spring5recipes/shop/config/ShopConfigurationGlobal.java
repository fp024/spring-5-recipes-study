package org.fp024.study.spring5recipes.shop.config;

import org.fp024.study.spring5recipes.shop.Cashier;
import org.fp024.study.spring5recipes.shop.ShopFileUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("global")
public class ShopConfigurationGlobal {
  @Bean(initMethod = "openFile", destroyMethod = "closeFile")
  public Cashier cashier() {

    String path = ShopFileUtils.getPath();
    Cashier c1 = new Cashier();
    c1.setFileName(ShopFileUtils.getFileNameOnly());
    c1.setPath(path);
    return c1;
  }
}
