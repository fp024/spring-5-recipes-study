package org.fp024.study.spring5recipes.shop;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class MainTest {
  @Test
  void testMain() throws Exception {
    Path path = Paths.get(ShopFileUtils.getFileNameWithPath(), "");
    if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
      Files.delete(path);
    } else {
      LOGGER.info("file not existed...");
    }

    LOGGER.info("fileName: {}", path.getFileName());
    Main.main(null);

    List<String> lines = Files.readAllLines(path);

    lines.forEach(line -> LOGGER.info("\t{}", line));

    if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
      Files.delete(path);
      LOGGER.info("file deleted...");
    }
  }
}
