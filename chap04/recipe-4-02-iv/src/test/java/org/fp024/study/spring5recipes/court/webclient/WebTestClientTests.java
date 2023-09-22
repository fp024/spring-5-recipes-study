package org.fp024.study.spring5recipes.court.webclient;

import org.fp024.study.spring5recipes.court.config.RootConfiguration;
import org.fp024.study.spring5recipes.court.config.ServletConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitWebConfig(classes = {RootConfiguration.class, ServletConfiguration.class})
class WebTestClientTests {

  @Autowired WebApplicationContext wac;

  WebTestClient client;

  @BeforeEach
  void setUp() {
    client = MockMvcWebTestClient.bindToApplicationContext(this.wac).build();
  }

  @Test
  void testMemberJson() {
    client
        .get()
        .uri("/member/1")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.name")
        .isEqualTo("Marten Deinum")
        .jsonPath("$.phone")
        .isEqualTo("00-31-1234567890");
  }

  @Test
  void testMemberXml() {
    client
        .get()
        .uri("/member/1")
        .accept(MediaType.APPLICATION_XML)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentTypeCompatibleWith(MediaType.APPLICATION_XML)
        .expectBody()
        .xpath("/member/name")
        .isEqualTo("Marten Deinum")
        .xpath("/member/phone")
        .isEqualTo("00-31-1234567890");
  }
}
