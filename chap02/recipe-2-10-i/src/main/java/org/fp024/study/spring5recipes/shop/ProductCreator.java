package org.fp024.study.spring5recipes.shop;

public class ProductCreator {
  // 팩토리 메서드
  public static Product createProduct(String productId) {
    if ("aaa".equals(productId)) {
      return new Battery("AAA", 2.5);
    } else if ("cdrw".equals(productId)) {
      return new Disc("CD-RW", 1.5);
    } else if ("dvdrw".equals(productId)) {
      return new Disc("DVD-RW", 3.0);
    }
    throw new IllegalArgumentException("Unknown product");
  }
}
