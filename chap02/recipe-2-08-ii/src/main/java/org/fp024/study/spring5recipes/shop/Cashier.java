package org.fp024.study.spring5recipes.shop;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Cashier {
  @Setter
  @Value(ShopFileUtils.FILE_NAME)
  private String fileName;

  @Value("#{systemProperties['java.io.tmpdir']}" + "/cashier")
  @Setter
  private String path;

  private BufferedWriter writer;

  @PostConstruct
  public void openFile() throws IOException {
    LOGGER.info("system temp path: {}", path);

    File targetDir = new File(path);
    if (!targetDir.exists()) {
      targetDir.mkdir();
    }

    File checkoutFile = new File(path, fileName + ".txt");
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

  @PreDestroy
  public void closeFile() throws IOException {
    writer.close();
  }
}
