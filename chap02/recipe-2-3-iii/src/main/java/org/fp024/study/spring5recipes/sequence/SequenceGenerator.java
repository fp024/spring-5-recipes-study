package org.fp024.study.spring5recipes.sequence;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
public class SequenceGenerator {
  // 배열형 프로퍼티에 @Autowired를 붙이면 매치된 빈을 모두 찾아 자동열결함.
  @Autowired @Setter private PrefixGenerator[] prefixGenerators;

  @Setter private String suffix;

  @Setter private int initial;

  private AtomicInteger counter = new AtomicInteger();

  public SequenceGenerator(PrefixGenerator[] prefixGenerators, String suffix, int initial) {
    this.prefixGenerators = prefixGenerators;
    this.suffix = suffix;
    this.initial = initial;
  }

  public String getSequence() {
    StringBuilder builder = new StringBuilder();
    for (PrefixGenerator prefix : prefixGenerators) {
      builder.append(prefix.getPrefix());
      builder.append("-");
    }
    builder.append(initial).append(counter.getAndIncrement()).append(suffix);
    return builder.toString();
  }
}
