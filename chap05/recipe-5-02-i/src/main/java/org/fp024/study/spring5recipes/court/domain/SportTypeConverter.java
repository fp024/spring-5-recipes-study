package org.fp024.study.spring5recipes.court.domain;

import org.fp024.study.spring5recipes.court.service.ReservationService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SportTypeConverter implements Converter<String, SportType> {
  private final ReservationService reservationService;

  public SportTypeConverter(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @Override
  public SportType convert(String source) {
    int sportTypeId = Integer.parseInt(source);
    return reservationService.getSportType(sportTypeId);
  }
}
