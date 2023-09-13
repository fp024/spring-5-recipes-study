package org.fp024.study.spring5recipes.court.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fp024.study.spring5recipes.court.service.ReservationServiceImpl.SOCCER;
import static org.fp024.study.spring5recipes.court.service.ReservationServiceImpl.TENNIS;

import java.time.LocalDate;
import java.util.List;
import org.fp024.study.spring5recipes.court.config.CourtConfiguration;
import org.fp024.study.spring5recipes.court.domain.Player;
import org.fp024.study.spring5recipes.court.domain.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

// 설정들이 분리되지 않아서 SpringJUnitWebConfig로 사용할 수 밖에 없다.
@SpringJUnitWebConfig(classes = {CourtConfiguration.class})
class ReservationServiceImplTests {
  @Autowired private ReservationService service;

  @BeforeEach
  void beforeEach() {
    service.resetData();
  }

  @Test
  void query() {
    List<Reservation> result = service.query("Tennis #2");

    assertThat(result).hasSize(1);
    assertThat(result.get(0)).hasFieldOrPropertyWithValue("courtName", "Tennis #2");
  }

  @Test
  void findByDate() {
    List<Reservation> result = service.findByDate(LocalDate.of(2023, 1, 1));
    assertThat(result).isNotEmpty();
    result.forEach(r -> assertThat(r.getDate()).isEqualTo(LocalDate.of(2023, 1, 1)));
  }

  @Test
  void getSportType() {
    assertThat(service.getSportType(1)).isEqualTo(TENNIS);
    assertThat(service.getSportType(2)).isEqualTo(SOCCER);
  }

  @Test
  void make() {
    // 1. 예약 일시가 중복되지 않음
    Reservation reservation1 =
        Reservation.builder() //
            .courtName("Tennis #1")
            .date(LocalDate.of(2008, 1, 14))
            .hour(15)
            .player(new Player("fp024", "N/A"))
            .sportType(TENNIS)
            .build();
    service.make(reservation1);

    // 1. 예약 일시가 중복되지 않음
    Reservation reservation2 =
        Reservation.builder() //
            .courtName("Tennis #1")
            .date(LocalDate.of(2008, 1, 14))
            .hour(16)
            .player(new Player("fp024", "N/A"))
            .sportType(TENNIS)
            .build();

    assertThatThrownBy(() -> service.make(reservation2))
        .isInstanceOf(ReservationNotAvailableException.class);
  }
}
