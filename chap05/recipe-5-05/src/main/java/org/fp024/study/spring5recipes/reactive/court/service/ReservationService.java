package org.fp024.study.spring5recipes.reactive.court.service;

import org.fp024.study.spring5recipes.reactive.court.domain.Reservation;
import reactor.core.publisher.Flux;

public interface ReservationService {

  Flux<Reservation> query(String query);

  Flux<Reservation> findAll();
}
