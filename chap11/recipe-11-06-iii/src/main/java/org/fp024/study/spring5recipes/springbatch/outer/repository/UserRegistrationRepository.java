package org.fp024.study.spring5recipes.springbatch.outer.repository;

import org.fp024.study.spring5recipes.springbatch.outer.domain.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Long> {}
