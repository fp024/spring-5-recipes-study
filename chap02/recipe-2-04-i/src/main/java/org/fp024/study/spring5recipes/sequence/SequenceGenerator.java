package org.fp024.study.spring5recipes.sequence;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class SequenceGenerator {
  @Resource(name = "datePrefixGenerator")
  @Setter private PrefixGenerator prefixGenerator;

  @Setter private String suffix;

  @Setter private int initial;

  private final AtomicInteger counter = new AtomicInteger();

  public SequenceGenerator(PrefixGenerator prefixGenerator, String suffix, int initial) {
    this.prefixGenerator = prefixGenerator;
    this.suffix = suffix;
    this.initial = initial;
  }

  public String getSequence() {
    StringBuilder builder = new StringBuilder();
    builder.append(prefixGenerator.getPrefix());
    builder.append(initial).append(counter.getAndIncrement());
    builder.append(suffix);
    return builder.toString();
  }
}
