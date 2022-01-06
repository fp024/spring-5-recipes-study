package org.fp024.study.spring5recipes.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/** 할인률을 적용해서 Product 생성 */
@RequiredArgsConstructor
public class DiscountFactoryBean extends AbstractFactoryBean<Product> {

  private final Product product;
  private final double discount;

  @Override
  public Class<?> getObjectType() {
    return product.getClass();
  }

  @Override
  protected Product createInstance() throws Exception {
    product.setPrice(product.getPrice() * (1 - discount));
    return product;
  }
}
