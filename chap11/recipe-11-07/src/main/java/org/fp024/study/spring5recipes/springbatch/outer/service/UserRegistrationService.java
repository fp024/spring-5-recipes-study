package org.fp024.study.spring5recipes.springbatch.outer.service;

import java.time.LocalDate;
import java.util.List;
import org.fp024.study.spring5recipes.springbatch.outer.dto.UserRegistrationDTO;

public interface UserRegistrationService {
  // 데이터 로드는 CSV 파일에서 로드하므로 아래 메서드는 사용하지 않는다.

  List<UserRegistrationDTO> getOutstandingUserRegistrationBatchForDate(
      int page, int quantity, LocalDate registerDate);

  UserRegistrationDTO registerUser(UserRegistrationDTO userRegistrationRegistrationDTO);
}
