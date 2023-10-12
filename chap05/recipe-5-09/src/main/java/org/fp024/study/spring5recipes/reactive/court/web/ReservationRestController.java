package org.fp024.study.spring5recipes.reactive.court.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.reactive.court.domain.Reservation;
import org.fp024.study.spring5recipes.reactive.court.service.ReservationService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ReservationRestController {

  private final ReservationService reservationService;

  public Mono<ServerResponse> listAll(ServerRequest request) {
    return ServerResponse.ok()
        .contentType(getAccept(request))
        .body(reservationService.findAll(), Reservation.class);
  }

  public Mono<ServerResponse> find(ServerRequest request) {
    return ServerResponse //
        .ok()
        .contentType(getAccept(request))
        .body(
            request
                .bodyToMono(ReservationQuery.class)
                .flatMapMany(q -> reservationService.query(q.getCourtName())),
            Reservation.class);
  }

  private MediaType getAccept(ServerRequest request) {
    List<MediaType> acceptList = request.headers().accept();
    return acceptList.contains(MediaType.APPLICATION_NDJSON)
        ? MediaType.APPLICATION_NDJSON
        : MediaType.APPLICATION_JSON;
  }
}
