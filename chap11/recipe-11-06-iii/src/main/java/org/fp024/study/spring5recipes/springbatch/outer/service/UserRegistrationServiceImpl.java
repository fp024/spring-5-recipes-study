package org.fp024.study.spring5recipes.springbatch.outer.service;

import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.springbatch.config.DeadlockTestHelper;
import org.fp024.study.spring5recipes.springbatch.outer.domain.UserRegistration;
import org.fp024.study.spring5recipes.springbatch.outer.dto.UserRegistrationDTO;
import org.fp024.study.spring5recipes.springbatch.outer.repository.UserRegistrationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserRegistrationServiceImpl implements UserRegistrationService {
  private final ModelMapper modelMapper;

  private final DeadlockTestHelper testHelper;

  private final UserRegistrationRepository userRegistrationRepository;

  @Retryable(backoff = @Backoff(delay = 3000, maxDelay = 100000, multiplier = 2.0))
  @Override
  public UserRegistrationDTO registerUser(UserRegistrationDTO userRegistrationDTO) {
    testHelper.process(userRegistrationDTO);

    UserRegistration userRegistration =
        modelMapper.map(userRegistrationDTO, UserRegistration.class);
    userRegistrationRepository.save(userRegistration);

    // Retry 템플릿에서 실패시 재시도 처리를 위해서 전달 되었던 인자를 다시 리턴하는게 요구사항 같음.
    return userRegistrationDTO;
  }
}
