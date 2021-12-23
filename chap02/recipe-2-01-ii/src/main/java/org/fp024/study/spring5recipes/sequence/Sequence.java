package org.fp024.study.spring5recipes.sequence;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class Sequence {
  private final String id;
  private final String prefix;
  private final String suffix;
}
