package org.fp024.study.spring5recipes.reactive.court.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.fp024.study.spring5recipes.reactive.court.domain.Player;
import org.fp024.study.spring5recipes.reactive.court.domain.Reservation;
import org.fp024.study.spring5recipes.reactive.court.domain.SportType;
import org.fp024.study.spring5recipes.reactive.court.exception.ReservationNotAvailableException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class InMemoryReservationService implements ReservationService {
  public static final SportType TENNIS = new SportType(1, "Tennis");
  public static final SportType SOCCER = new SportType(2, "Soccer");

  private final List<Reservation> reservations = new ArrayList<>();

  public InMemoryReservationService() {

    reservations.add(
        new Reservation(
            "Tennis #1", LocalDate.of(2008, 1, 14), 16, new Player("Roger", "N/A"), TENNIS));
    reservations.add(
        new Reservation(
            "Tennis #2", LocalDate.of(2008, 1, 14), 20, new Player("James", "N/A"), TENNIS));
  }

  @Override
  public Mono<Reservation> make(Reservation reservation) {

    long cnt =
        reservations.stream()
            .filter(made -> Objects.equals(made.getCourtName(), reservation.getCourtName()))
            .filter(made -> Objects.equals(made.getDate(), reservation.getDate()))
            .filter(made -> made.getHour() == reservation.getHour())
            .count();

    if (cnt > 0) {
      return Mono.error(
          new ReservationNotAvailableException(
              reservation.getCourtName(), reservation.getDate(), reservation.getHour()));
    } else {
      reservations.add(reservation);
      return Mono.just(reservation);
    }
  }

  @Override
  public SportType getSportType(int sportTypeId) {
    return switch (sportTypeId) {
      case 1 -> TENNIS;
      case 2 -> SOCCER;
      default -> null;
    };
  }

  @Override
  public Flux<Reservation> query(String courtName) {
    if (courtName != null) {
      return findAll().filter(r -> r.getCourtName().startsWith(courtName));
    }
    return Flux.empty();
  }

  @Override
  public Flux<Reservation> findAll() {
    return Flux.fromIterable(reservations);
  }

  @Override
  public Flux<SportType> getAllSportTypes() {
    return Flux.fromIterable(List.of(TENNIS, SOCCER));
  }
}
