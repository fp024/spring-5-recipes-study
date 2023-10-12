package org.fp024.study.spring5recipes.reactive.court;

import java.io.IOException;
import org.fp024.study.spring5recipes.reactive.court.domain.Reservation;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class Main {

  public static void main(String[] args) throws IOException {
    final String url = "http://localhost:8080";

    WebClient.create(url)
        .get()
        .uri("/reservations")
        .accept(MediaType.APPLICATION_NDJSON)
        .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Reservation.class))
        .subscribe(System.out::println);

    System.in.read(); // 아무 키나 입력 받을 때까지 대기
  }
}
