package org.fp024.spring5recipes.study.hello;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloWorld {
  @Getter @Setter private List<Holiday> holidays;
  @Setter private String message;

  public void hello() {
    LOGGER.info("Hello! {}", message);
  }
}
