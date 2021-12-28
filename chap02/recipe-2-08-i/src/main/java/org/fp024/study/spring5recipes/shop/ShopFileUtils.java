package org.fp024.study.spring5recipes.shop;

public class ShopFileUtils {
  private static final String FILE_NAME = "checkout";
  private static final String FILE_EXT = "txt";

  public static String getPath() {
    return System.getProperty("java.io.tmpdir") + "/cashier";
  }

  public static String getFileNameWithPath() {
    return getPath() + "/" + getFileName();
  }

  public static String getFileName() {
    return getFileNameOnly() + "." + FILE_EXT;
  }

  public static String getFileNameOnly() {
    return FILE_NAME;
  }
}
