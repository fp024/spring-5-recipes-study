package org.fp024.study.spring5recipes.springbatch.config;

import java.time.LocalDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.springbatch.UserRegistration;
import org.fp024.study.spring5recipes.springbatch.service.UserRegistrationService;
import org.springframework.batch.item.ItemReader;

@RequiredArgsConstructor
public class UserRegistrationItemReader implements ItemReader<UserRegistration> {

  private final UserRegistrationService userRegistrationService;

  /**
   * 입력 데이터를 읽고 다음 데이터로 넘어갑니다. <br>
   * 구현은 입력 데이터 세트의 끝에서 null을 반환해야 합니다. <br>
   * 트랜잭션 설정에서 첫 번째 호출이 롤백된 트랜잭션에 있는 경우 <br>
   * 호출자는 연속 호출(또는 그렇지 않은 경우)에서 동일한 항목을 두 번 얻을 수 있습니다.
   */
  @Override
  public UserRegistration read() throws Exception {
    final LocalDateTime today = LocalDateTime.now();
    Collection<UserRegistration> registrations =
        userRegistrationService.getOutstandingUserRegistrationBatchForDate(1, today);
    return registrations.stream().findFirst().orElse(null);
  }
}
