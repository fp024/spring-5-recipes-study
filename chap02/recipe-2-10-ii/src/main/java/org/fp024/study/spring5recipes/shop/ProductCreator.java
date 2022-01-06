package org.fp024.study.spring5recipes.shop;

import java.util.Map;

public class ProductCreator {
  // 인스턴스 팩토리 메서드
  private Map<String, Product> products;

  public void setProducts(Map<String, Product> products) {
    this.products = products;
  }

  public Product createProduct(String productId) {
    Product product = products.get(productId);
    if (product != null) {
      return product;
    }
    throw new IllegalArgumentException("Unknown product");
  }
}
