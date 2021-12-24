package org.fp024.study.spring5recipes.shop;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.core.io.Resource;

public class BannerLoader {
  @Setter private Resource banner;

  @PostConstruct
  public void showBanner() throws IOException {
    Files.lines(Paths.get(banner.getURI()), Charset.forName("UTF-8"))
        .forEachOrdered(System.out::println);
  }
}
