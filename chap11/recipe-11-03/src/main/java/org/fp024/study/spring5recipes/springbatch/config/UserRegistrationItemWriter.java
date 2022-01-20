package org.fp024.study.spring5recipes.springbatch.config;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.springbatch.UserRegistration;
import org.fp024.study.spring5recipes.springbatch.service.UserRegistrationService;
import org.springframework.batch.item.ItemWriter;

@Slf4j
public class UserRegistrationItemWriter implements ItemWriter<UserRegistration> {

  // 이것은 HTTP 호출자 서비스에 대한 클라이언트 인터페이스입니다.
  private final UserRegistrationService userRegistrationService;

  public UserRegistrationItemWriter(UserRegistrationService userRegistrationService) {
    this.userRegistrationService = userRegistrationService;
  }

  // Reader로부터 집계된 입력을 받아 사용자 정의 구현을 사용하여 'Write'합니다.
  public void write(List<? extends UserRegistration> items) throws Exception {
    items.forEach(this::write);
  }

  private void write(UserRegistration userRegistration) {
    UserRegistration registeredUserRegistration =
        userRegistrationService.registerUser(userRegistration);
    LOGGER.debug("Registered: {}", registeredUserRegistration);
  }
}
