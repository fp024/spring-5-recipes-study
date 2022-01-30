package org.fp024.study.spring5recipes.springbatch.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.springbatch.UserRegistration;

@Slf4j
public class UserRegistrationServiceImpl implements UserRegistrationService {

  // 제공될 Mock 유저
  private final Collection<UserRegistration> mockUserList = makeMockUser(5);

  @Override
  public Collection<UserRegistration> getOutstandingUserRegistrationBatchForDate(
      int quantity, LocalDateTime dateTime) {
    Collection<UserRegistration> newList = new ArrayList<>(mockUserList);

    // 최초에 한번 읽어가고, 이후 읽을게 없도록 비운다.
    mockUserList.clear();
    return newList;
  }

  @Override
  public UserRegistration registerUser(UserRegistration userRegistrationRegistration) {
    LOGGER.info("register user: {}", userRegistrationRegistration);
    return userRegistrationRegistration;
  }

  private Collection<UserRegistration> makeMockUser(int quantity) {
    return IntStream.rangeClosed(1, quantity)
        .mapToObj(
            i ->
                new UserRegistration(
                    "firstName_" + i,
                    "lastName_" + i,
                    "company_" + i,
                    "address_" + i,
                    "city_" + i,
                    "state_" + i,
                    "zip_" + i,
                    "county_" + i,
                    "url_" + i,
                    "phoneNumber_" + i,
                    "fax_" + i))
        .collect(Collectors.toList());
  }
}
