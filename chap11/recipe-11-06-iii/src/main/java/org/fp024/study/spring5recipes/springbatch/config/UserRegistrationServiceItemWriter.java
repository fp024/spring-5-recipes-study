package org.fp024.study.spring5recipes.springbatch.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.springbatch.outer.dto.UserRegistrationDTO;
import org.fp024.study.spring5recipes.springbatch.outer.service.UserRegistrationService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserRegistrationServiceItemWriter implements ItemWriter<UserRegistrationDTO> {
  private final UserRegistrationService userRegistrationService;

  @Override
  public void write(List<? extends UserRegistrationDTO> items) throws Exception {
    for (UserRegistrationDTO userRegistrationDTO : items) {
      UserRegistrationDTO registeredUserRegistrationDTO =
          userRegistrationService.registerUser(userRegistrationDTO);
      LOGGER.debug("Registered: {}", registeredUserRegistrationDTO);
    }
  }
}
