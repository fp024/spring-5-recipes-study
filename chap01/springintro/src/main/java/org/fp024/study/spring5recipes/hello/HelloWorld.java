package org.fp024.study.spring5recipes.hello;

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
