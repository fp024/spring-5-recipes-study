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
    // Cashier 자체에서 파일 이름을 빈 이름으로 설정해서, 이름을 설정해 줄 필요가 없음.
    // c1.setFileName(ShopFileUtils.getFileNameOnly());
    c1.setPath(path);
    return c1;
  }
}
