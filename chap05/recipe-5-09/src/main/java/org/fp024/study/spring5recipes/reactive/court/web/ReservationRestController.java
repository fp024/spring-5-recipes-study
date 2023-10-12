package org.fp024.study.spring5recipes.reactive.court.web;

import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.reactive.court.domain.Reservation;
import org.fp024.study.spring5recipes.reactive.court.service.ReservationService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ReservationRestController {

  private final ReservationService reservationService;

  @GetMapping
  public Mono<ServerResponse> listAll(@SuppressWarnings("unused") ServerRequest request) {
    return ServerResponse.ok().body(reservationService.findAll(), Reservation.class);
  }

  @PostMapping
  public Mono<ServerResponse> find(ServerRequest request) {
    return ServerResponse //
        .ok()
        .body(
            request
                .bodyToMono(ReservationQuery.class)
                .flatMapMany(q -> reservationService.query(q.getCourtName())),
            Reservation.class);
  }
}
