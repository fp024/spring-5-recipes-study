package org.fp024.study.spring5recipes.shop;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Setter;

public class Cashier {
  @Setter private String fileName;
  @Setter private String path;

  private BufferedWriter writer;

  public void openFile() throws IOException {

    File targetDir = new File(path);
    if (!targetDir.exists()) {
      targetDir.mkdir();
    }

    File checkoutFile = new File(path, fileName + "." + ShopFileUtils.getFileExtension());
    if (!checkoutFile.exists()) {
      checkoutFile.createNewFile();
    }

    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(checkoutFile, true)));
  }

  public void checkout(ShoppingCart cart) throws IOException {
    writer.write(
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            + "\t"
            + cart.getItems()
            + System.lineSeparator());
    writer.flush();
  }

  public void closeFile() throws IOException {
    writer.close();
  }
}
