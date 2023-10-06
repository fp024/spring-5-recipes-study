package org.fp024.study.spring5recipes.court;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import org.fp024.study.spring5recipes.court.domain.Player;
import org.fp024.study.spring5recipes.court.domain.Reservation;
import org.fp024.study.spring5recipes.court.domain.SportType;
import org.fp024.study.spring5recipes.court.service.ReservationNotAvailableException;
import org.fp024.study.spring5recipes.court.service.ReservationService;
import org.springframework.stereotype.Component;

/** 저자님이 그래도 데이터 초기화 코드를 주닙해주심. 👍 */
@Component
public class DataInitializer {

  private static final List<String> NAMES = Arrays.asList("Roger", "James", "Marten", "Josh");
  private final ReservationService reservationService;
  private final Random rnd = new Random();

  public DataInitializer(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @PostConstruct
  public void init() {

    List<SportType> sportTypes = reservationService.getAllSportTypes();

    for (int i = 0; i < 200; i++) {
      int type = rnd.nextInt(sportTypes.size());
      int courtNum = rnd.nextInt(3);
      SportType sportType = sportTypes.get(type);
      String court = sportType.getName() + " #" + courtNum;

      String name = NAMES.get(rnd.nextInt(NAMES.size()));

      try {
        reservationService.make(
            new Reservation(
                court,
                LocalDate.of(2017, rnd.nextInt(12) + 1, rnd.nextInt(28) + 1),
                rnd.nextInt(24),
                new Player(name, "N/A"),
                sportType));
      } catch (ReservationNotAvailableException e) {
        // 랜덤에 따라 중복이 생길 수도 있는데, 이때 넘어가자
      }
    }
  }
}
