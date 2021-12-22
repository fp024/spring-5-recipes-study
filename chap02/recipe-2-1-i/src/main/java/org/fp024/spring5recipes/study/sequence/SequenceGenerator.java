package org.fp024.spring5recipes.study.sequence;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class SequenceGenerator {
  @Setter private String prefix;
  @Setter private String suffix;
  @Setter private int initial;

  private final AtomicInteger counter = new AtomicInteger();

  public String getSequence() {
    StringBuilder builder = new StringBuilder();
    builder.append(prefix).append(initial).append(counter.getAndIncrement()).append(suffix);
    return builder.toString();
  }
}
