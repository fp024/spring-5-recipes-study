package org.fp024.study.spring5recipes.sequence;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class DatePrefixGenerator implements PrefixGenerator {

  @Override
  public String getPrefix() {
    return DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now());
  }
}
