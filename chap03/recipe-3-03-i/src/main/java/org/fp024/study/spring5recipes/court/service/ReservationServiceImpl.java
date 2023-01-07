package org.fp024.study.spring5recipes.court.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.fp024.study.spring5recipes.court.domain.Player;
import org.fp024.study.spring5recipes.court.domain.Reservation;
import org.fp024.study.spring5recipes.court.domain.SportType;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

  private static final SportType TENNIS = new SportType(1, "Tennis");
  private static final SportType SOCCER = new SportType(2, "Soccer");

  private final List<Reservation> reservations = new ArrayList<>();

  public ReservationServiceImpl() {

    reservations.add(
        new Reservation(
            "Tennis #1", LocalDate.of(2008, 1, 14), 16, new Player("Roger", "N/A"), TENNIS));
    reservations.add(
        new Reservation(
            "Tennis #2", LocalDate.of(2008, 1, 14), 20, new Player("James", "N/A"), TENNIS));
    reservations.add(
        new Reservation(
            "Soccer #1", LocalDate.of(2023, 1, 14), 20, new Player("fp024", "N/A"), SOCCER));
  }

  @Override
  public List<Reservation> query(String courtName) {

    return this.reservations.stream()
        .filter(reservation -> Objects.equals(reservation.getCourtName(), courtName))
        .collect(Collectors.toList());
  }
}
