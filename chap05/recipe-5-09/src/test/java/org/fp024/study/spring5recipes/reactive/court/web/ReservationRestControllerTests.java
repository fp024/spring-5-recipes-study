package org.fp024.study.spring5recipes.reactive.court.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.fp024.study.spring5recipes.reactive.court.WebFluxConfiguration;
import org.fp024.study.spring5recipes.reactive.court.domain.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ListBodySpec;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitWebConfig(classes = {WebFluxConfiguration.class})
class ReservationRestControllerTests {
  @Autowired private WebApplicationContext wac;

  private WebTestClient client;

  @Autowired private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    client = WebTestClient.bindToApplicationContext(wac).build();
  }

  @Test
  void reservations_GET() {
    client
        .get() //
        .uri("/reservations")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Reservation.class);
  }

  @Test
  void reservations_POST() throws Exception {
    ListBodySpec<Reservation> response =
        client
            .post() //
            .uri("/reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(objectMapper.writeValueAsString(new ReservationQuery("Tennis #1")))
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(Reservation.class);

    List<Reservation> result = response.returnResult().getResponseBody();
    assertThat(result).isNotNull();
    result.forEach(r -> assertThat(r.getCourtName()).isEqualTo("Tennis #1"));
  }
}
