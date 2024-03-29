package org.fp024.study.spring5recipes.court.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import org.fp024.study.spring5recipes.court.domain.PeriodicReservation;
import org.fp024.study.spring5recipes.court.domain.Player;
import org.fp024.study.spring5recipes.court.domain.Reservation;
import org.fp024.study.spring5recipes.court.domain.SportType;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

  static final SportType TENNIS = new SportType(1, "Tennis");
  static final SportType SOCCER = new SportType(2, "Soccer");

  private final List<Reservation> reservations = new CopyOnWriteArrayList<>();

  public ReservationServiceImpl() {
    resetData();
  }

  @Override
  public void resetData() {
    reservations.clear();
    reservations.add(
        new Reservation(
            "Tennis #1", LocalDate.of(2008, 1, 14), 16, new Player("Roger", "N/A"), TENNIS));
    reservations.add(
        new Reservation(
            "Tennis #2", LocalDate.of(2008, 1, 14), 20, new Player("James", "N/A"), TENNIS));
    reservations.add(
        new Reservation(
            "Soccer #1", LocalDate.of(2023, 1, 1), 20, new Player("fp024", "N/A"), SOCCER));
    reservations.add(
        new Reservation(
            "Soccer #2", LocalDate.of(2023, 1, 1), 20, new Player("struts", "N/A"), SOCCER));
  }

  @Override
  public List<Reservation> query(String courtName) {

    return this.reservations.stream()
        .filter(reservation -> Objects.equals(reservation.getCourtName(), courtName))
        .collect(Collectors.toList());
  }

  @Override
  public List<Reservation> findByDate(LocalDate date) {
    return reservations.stream().filter(r -> r.getDate().equals(date)).toList();
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
  public void make(Reservation reservation) throws ReservationNotAvailableException {
    long cnt =
        reservations.stream()
            .filter(made -> Objects.equals(made.getCourtName(), reservation.getCourtName()))
            .filter(made -> Objects.equals(made.getDate(), reservation.getDate()))
            .filter(made -> made.getHour() == reservation.getHour())
            .count();

    if (cnt > 0) {
      throw new ReservationNotAvailableException(
          reservation.getCourtName(), reservation.getDate(), reservation.getHour());
    } else {
      reservations.add(reservation);
    }
  }

  @Override
  public List<SportType> getAllSportTypes() {
    return List.of(TENNIS, SOCCER);
  }

  @Override
  public void makePeriodic(PeriodicReservation periodicReservation)
      throws ReservationNotAvailableException {

    LocalDate fromDate = periodicReservation.getFromDate();

    while (fromDate.isBefore(periodicReservation.getToDate())) {
      Reservation reservation = new Reservation();
      reservation.setCourtName(periodicReservation.getCourtName());
      reservation.setDate(fromDate);
      reservation.setHour(periodicReservation.getHour());
      reservation.setPlayer(periodicReservation.getPlayer());
      make(reservation);

      // 이 부분이 해깔렸었는데, 컨트롤러와 JSP 코드 보고 알았음.
      // 매일할지, 일주일에 한번 예약할지 정하기 위해서 넣은 코드임.
      // 컨트롤러에 전역 ModelAttribute 설정 목록으로 매일/주 단위 2가지 메뉴로 정해져 있긴함.
      fromDate = fromDate.plusDays(periodicReservation.getPeriod());
    }
  }
}
