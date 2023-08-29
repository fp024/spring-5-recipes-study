package org.fp024.study.spring5recipes.springbatch.outer.repository;

import java.time.LocalDate;
import org.fp024.study.spring5recipes.springbatch.outer.domain.UserRegistration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Long> {
  Page<UserRegistration> findByRegisterDate(LocalDate date, Pageable pageable);
}
