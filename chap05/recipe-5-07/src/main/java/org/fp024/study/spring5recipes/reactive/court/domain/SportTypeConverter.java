package org.fp024.study.spring5recipes.reactive.court.domain;

import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.reactive.court.service.ReservationService;
import org.springframework.core.convert.converter.Converter;

@RequiredArgsConstructor
public class SportTypeConverter implements Converter<String, SportType> {

  private final ReservationService reservationService;

  @Override
  public SportType convert(String source) {
    int sportTypeId = Integer.parseInt(source);
    SportType sportType = reservationService.getSportType(sportTypeId);
    return sportType;
  }
}
