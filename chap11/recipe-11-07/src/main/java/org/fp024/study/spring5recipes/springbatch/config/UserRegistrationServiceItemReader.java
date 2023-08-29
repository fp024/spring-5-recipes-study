package org.fp024.study.spring5recipes.springbatch.config;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.springbatch.outer.dto.UserRegistrationDTO;
import org.fp024.study.spring5recipes.springbatch.outer.service.UserRegistrationService;
import org.springframework.batch.item.ItemReader;

// 이 리더는 빈으로 등록하지 말고 스탭에서 생성자 인자로 받아보자!
@RequiredArgsConstructor
@Slf4j
public class UserRegistrationServiceItemReader implements ItemReader<UserRegistrationDTO> {
  private final UserRegistrationService userRegistrationService;

  private final LocalDate date;

  private List<UserRegistrationDTO> items;
  // JPA PageRequest.of()에서 페이지 지정할 때 0이 1페이지.
  private int page = 0;
  private int quantity = 10;

  private int index = 0;

  @Override
  public UserRegistrationDTO read() {
    if (items == null || index >= items.size()) {
      items = fetchDataFromServiceDB();
      index = 0;
      page++;
    }
    UserRegistrationDTO item = null;
    if (index < items.size()) {
      item = items.get(index);
      index++;
    }
    return item;
  }

  private List<UserRegistrationDTO> fetchDataFromServiceDB() {
    return userRegistrationService.getOutstandingUserRegistrationBatchForDate(page, quantity, date);
  }
}
