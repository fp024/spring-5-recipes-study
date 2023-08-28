package org.fp024.study.spring5recipes.springbatch.config;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.springbatch.outer.dto.UserRegistrationDTO;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeadlockTestHelper {
  private final AtomicInteger count = new AtomicInteger(0);
  private static final int MAX_COUNT = 2;
  private static final int TARGET_ID_NUMBER = 7;

  public UserRegistrationDTO process(UserRegistrationDTO item) {
    LOGGER.info("### item: {}", item.getFirstName());
    if ("firstName_%s".formatted(TARGET_ID_NUMBER).equals(item.getFirstName())
        && count.get() < MAX_COUNT) {
      throw new DeadlockLoserDataAccessException(
          "count: %s".formatted(count.getAndIncrement()), null);
    }
    return item;
  }
}
