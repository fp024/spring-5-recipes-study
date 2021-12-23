package org.fp024.study.spring5recipes.sequence;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@NoArgsConstructor
public class SequenceGenerator {
  private PrefixGenerator prefixGenerator;

  @Setter private String suffix;

  @Setter private int initial;

  private int counter;

  public SequenceGenerator(PrefixGenerator prefixGenerator, String suffix, int initial) {
    this.prefixGenerator = prefixGenerator;
    this.suffix = suffix;
    this.initial = initial;
  }

  @Autowired
  public void setPrefixGenerator(PrefixGenerator prefixGenerator) {
    LOGGER.info("setPrefixGenerator: {}", prefixGenerator);
    this.prefixGenerator = prefixGenerator;
  }

  // 타입만 맞으면 기본으로 자동연결된다.
  @Autowired
  public void myOwnCustomInjectionName(PrefixGenerator prefixGenerator) {
    LOGGER.info("myOwnCustomInjectionName: {}", prefixGenerator);
    this.prefixGenerator = prefixGenerator;
  }

  public synchronized String getSequence() {
    StringBuilder builder = new StringBuilder();
    builder.append(prefixGenerator.getPrefix());
    builder.append(initial + counter++);
    builder.append(suffix);
    return builder.toString();
  }
}
