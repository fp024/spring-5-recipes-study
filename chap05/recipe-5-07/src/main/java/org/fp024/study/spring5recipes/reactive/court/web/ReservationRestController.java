package org.fp024.study.spring5recipes.reactive.court.web;

import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.reactive.court.domain.Reservation;
import org.fp024.study.spring5recipes.reactive.court.service.ReservationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservations")
public class ReservationRestController {

  private final ReservationService reservationService;

  @GetMapping
  public Flux<Reservation> listAll() {
    return reservationService.findAll();
  }

  @PostMapping
  public Flux<Reservation> find(@RequestBody Mono<ReservationQuery> query) {
    return query.flatMapMany(q -> reservationService.query(q.getCourtName()));
  }
}
