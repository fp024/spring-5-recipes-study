package org.fp024.study.spring5recipes.springbatch.service;

import java.time.LocalDateTime;
import java.util.Collection;
import org.fp024.study.spring5recipes.springbatch.UserRegistration;

public interface UserRegistrationService {
  Collection<UserRegistration> getOutstandingUserRegistrationBatchForDate(
      int quantity, LocalDateTime dateTime);

  UserRegistration registerUser(UserRegistration userRegistrationRegistration);
}
