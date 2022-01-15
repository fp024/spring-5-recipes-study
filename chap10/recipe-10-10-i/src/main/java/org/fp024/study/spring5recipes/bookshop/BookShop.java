package org.fp024.study.spring5recipes.bookshop;

public interface BookShop {
  void purchase(String isbn, String username);

  void increaseStock(String isbn, int stock);

  int checkStock(String isbn);

  // 기존에 재고를 확인하는 메서드가 있긴한데, Sleep이 들어가 있어서, 없는 메서드 추가해줬다.
  int getStockWithNoSleep(String isbn);
}
