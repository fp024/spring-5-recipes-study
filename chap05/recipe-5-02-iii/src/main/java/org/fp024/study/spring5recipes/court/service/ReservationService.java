package org.fp024.study.spring5recipes.court.service;

import java.time.LocalDate;
import java.util.List;
import org.fp024.study.spring5recipes.court.domain.Reservation;
import org.fp024.study.spring5recipes.court.domain.SportType;

public interface ReservationService {

  /** 테스트시 초기화를 위해 추가해둠. */
  void resetData();

  List<Reservation> query(String courtName);

  List<Reservation> findByDate(LocalDate date);

  SportType getSportType(int sportTypeId);

  void make(Reservation reservation) throws ReservationNotAvailableException;

  List<SportType> getAllSportTypes();
}
