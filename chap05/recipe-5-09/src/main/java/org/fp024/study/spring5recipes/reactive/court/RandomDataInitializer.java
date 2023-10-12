package org.fp024.study.spring5recipes.reactive.court;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.reactive.court.domain.Player;
import org.fp024.study.spring5recipes.reactive.court.domain.Reservation;
import org.fp024.study.spring5recipes.reactive.court.domain.SportType;
import org.fp024.study.spring5recipes.reactive.court.exception.ReservationNotAvailableException;
import org.fp024.study.spring5recipes.reactive.court.service.ReservationService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Component
public class RandomDataInitializer {

  private static final List<String> NAMES = List.of("Roger", "James", "Marten", "Josh");
  private final ReservationService reservationService;
  private final Random rnd = new Random();

  @PostConstruct
  public void init() {
    Flux<Reservation> reservations = Flux.empty();
    for (int i = 0; i < 100; i++) {
      int courtNum = rnd.nextInt(3);
      SportType sportType = reservationService.getAllSportTypes().take(1).blockFirst();
      String court = sportType.getName() + " #" + courtNum;

      String name = NAMES.get(rnd.nextInt(NAMES.size()));

      try {
        Reservation res =
            new Reservation(
                court,
                LocalDate.of(2017, rnd.nextInt(12) + 1, rnd.nextInt(28) + 1),
                rnd.nextInt(24),
                new Player(name, "N/A"),
                sportType);
        reservations.concatWith(reservationService.make(res));

      } catch (ReservationNotAvailableException e) {
      }
    }
    reservations.log().subscribe();
  }
}
