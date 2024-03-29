package org.fp024.study.spring5recipes.reactive.court.domain;

import org.fp024.study.spring5recipes.reactive.court.service.ReservationService;
import org.springframework.core.convert.converter.Converter;

public class SportTypeConverter implements Converter<String, SportType> {

  private final ReservationService reservationService;

  public SportTypeConverter(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @Override
  public SportType convert(String source) {
    int sportTypeId = Integer.parseInt(source);
    SportType sportType = reservationService.getSportType(sportTypeId);
    return sportType;
  }
}
