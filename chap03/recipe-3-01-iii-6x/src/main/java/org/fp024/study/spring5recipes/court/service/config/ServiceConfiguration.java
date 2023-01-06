package org.fp024.study.spring5recipes.court.service.config;

import org.fp024.study.spring5recipes.court.service.ReservationService;
import org.fp024.study.spring5recipes.court.service.ReservationServiceImpl;
import org.springframework.context.annotation.Bean;

public class ServiceConfiguration {
  @Bean
  public ReservationService reservationService() {
    return new ReservationServiceImpl();
  }
}
