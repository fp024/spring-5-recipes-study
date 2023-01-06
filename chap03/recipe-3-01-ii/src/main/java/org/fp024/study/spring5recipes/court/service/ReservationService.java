package org.fp024.study.spring5recipes.court.service;

import java.util.List;

import org.fp024.study.spring5recipes.court.domain.Reservation;

public interface ReservationService {
  List<Reservation> query(String courtName);
}
