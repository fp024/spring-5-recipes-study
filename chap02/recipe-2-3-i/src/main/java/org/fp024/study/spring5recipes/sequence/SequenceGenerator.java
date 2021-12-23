package org.fp024.study.spring5recipes.sequence;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class SequenceGenerator {

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
    String builder = prefixGenerator.getPrefix() + initial + counter.getAndIncrement() + suffix;
    return builder;
  }
}
