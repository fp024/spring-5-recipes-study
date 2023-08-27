package org.fp024.study.spring5recipes.springbatch.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.springbatch.outer.dto.UserRegistrationDTO;
import org.fp024.study.spring5recipes.springbatch.outer.service.UserRegistrationService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RetryableUserRegistrationServiceItemWriter implements ItemWriter<UserRegistrationDTO> {
  private final UserRegistrationService userRegistrationService;
  private final RetryTemplate retryTemplate;

  @Override
  public void write(List<? extends UserRegistrationDTO> items) throws Exception {
    for (UserRegistrationDTO userRegistrationDTO : items) {
      UserRegistrationDTO registeredUserRegistrationDTO =
          retryTemplate.execute(
              context -> userRegistrationService.registerUser(userRegistrationDTO));

      LOGGER.debug("Registered: {}", registeredUserRegistrationDTO);
    }
  }
}
