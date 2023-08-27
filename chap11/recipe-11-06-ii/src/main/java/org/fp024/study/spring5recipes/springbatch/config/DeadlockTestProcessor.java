package org.fp024.study.spring5recipes.springbatch.config;

import java.util.concurrent.atomic.AtomicInteger;
import org.fp024.study.spring5recipes.springbatch.outer.dto.UserRegistrationDTO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.dao.DeadlockLoserDataAccessException;

public class DeadlockTestProcessor
    implements ItemProcessor<UserRegistrationDTO, UserRegistrationDTO> {
  private final AtomicInteger count = new AtomicInteger(0);
  private static final int MAX_COUNT = 2;
  private static final int TARGET_ID_NUMBER = 1;

  @Override
  public UserRegistrationDTO process(UserRegistrationDTO item) {

    if ("firstName_%s".formatted(TARGET_ID_NUMBER).equals(item.getFirstName())
        && count.get() < MAX_COUNT) {
      throw new DeadlockLoserDataAccessException(
          "count: %s".formatted(count.getAndIncrement()), null);
    }
    return item;
  }
}
