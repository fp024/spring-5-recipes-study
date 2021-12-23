package org.fp024.study.spring5recipes.sequence;

public interface SequenceDao {
  Sequence getSequence(String sequenceId);

  int getNextValue(String sequenceId);
}
