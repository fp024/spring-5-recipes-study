package org.fp024.study.spring5recipes.sequence;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@DatePrefixAnnotation
public class DatePrefixGenerator implements PrefixGenerator {

  private DateTimeFormatter formatter;

  public void setPattern(String pattern) {
    this.formatter = DateTimeFormatter.ofPattern(pattern);
  }

  @Override
  public String getPrefix() {
    return formatter.format(LocalDateTime.now());
  }
}
