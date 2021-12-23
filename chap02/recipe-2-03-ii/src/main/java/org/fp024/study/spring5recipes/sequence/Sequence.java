package org.fp024.study.spring5recipes.sequence;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Sequence {
  private final String id;
  private final String prefix;
  private final String suffix;
}
