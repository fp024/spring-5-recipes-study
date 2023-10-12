package org.fp024.study.spring5recipes.reactive.court.web;

import org.fp024.study.spring5recipes.reactive.court.WebFluxConfiguration;
import org.fp024.study.spring5recipes.reactive.court.domain.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;
import reactor.core.publisher.Flux;

@Disabled("테스트를 만들어보면 좋은데, 아직 리액티브 프로그래밍에 대해 몰라서, 천천히 해봐야겠다.")
@SpringJUnitWebConfig(classes = {WebFluxConfiguration.class})
class ReservationRestControllerTests {
  @Autowired private WebApplicationContext wac;

  private WebTestClient client;

  @BeforeEach
  void setUp() {
    client = MockMvcWebTestClient.bindToApplicationContext(this.wac).build();
  }

  @Test
  void reservations_GET() {
    Flux<Reservation> result =
        client
            .get() //
            .uri("/reservations")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(Reservation.class)
            .getResponseBody();
  }
}
