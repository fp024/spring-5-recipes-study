package org.fp024.study.spring5recipes.reactive.court.service;

import org.fp024.study.spring5recipes.reactive.court.domain.Reservation;
import org.fp024.study.spring5recipes.reactive.court.domain.SportType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReservationService {

  Flux<Reservation> query(String query);

  Flux<Reservation> findAll();

  Flux<SportType> getAllSportTypes();

  Mono<Reservation> make(Reservation reservation);

  SportType getSportType(int sportTypeId);
}
