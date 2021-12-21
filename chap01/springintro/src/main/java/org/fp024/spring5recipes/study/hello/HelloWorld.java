package org.fp024.spring5recipes.study.hello;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class HelloWorld {
  @Getter @Setter private List<Holiday> holidays;
  @Setter private String message;

  public void hello() {
    LOGGER.info("Hello! {}", message);
  }
}
