package org.fp024.study.spring5recipes.springbatch.outer.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.springbatch.outer.domain.UserRegistration;
import org.fp024.study.spring5recipes.springbatch.outer.dto.UserRegistrationDTO;
import org.fp024.study.spring5recipes.springbatch.outer.repository.UserRegistrationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserRegistrationServiceImpl implements UserRegistrationService {
  private final ModelMapper modelMapper;

  private final UserRegistrationRepository userRegistrationRepository;

  /*
   한번에 가져오는 양(quantity)을 chunk로 맞추는게 좋을 수도 있다고는 함.
  */
  @Override
  public List<UserRegistrationDTO> getOutstandingUserRegistrationBatchForDate(
      int page, int quantity, LocalDate date) {

    Page<UserRegistration> result =
        userRegistrationRepository.findByRegisterDate(date, PageRequest.of(page, quantity));

    return result.getContent().stream()
        .map(u -> modelMapper.map(u, UserRegistrationDTO.class))
        .toList();
  }

  @Retryable(backoff = @Backoff(delay = 3000, maxDelay = 100000, multiplier = 2.0))
  @Override
  public UserRegistrationDTO registerUser(UserRegistrationDTO userRegistrationDTO) {
    UserRegistration userRegistration =
        modelMapper.map(userRegistrationDTO, UserRegistration.class);

    userRegistration.setRegisterDate(LocalDate.now());
    userRegistrationRepository.save(userRegistration);

    // Retry 템플릿에서 실패시 재시도 처리를 위해서 전달 되었던 인자를 다시 리턴하는게 요구사항 같음.
    return userRegistrationDTO;
  }
}
