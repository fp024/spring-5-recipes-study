package org.fp024.study.spring5recipes.board.controller;

import org.fp024.study.spring5recipes.board.config.TodoRootConfig;
import org.fp024.study.spring5recipes.board.config.TodoWebConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;

/*
 * WebTestClient는 일반 웹페이지 컨트롤러 메서드 테스트하는데는 안 어울린다.
 * REST API 테스트 할 때만 사용하는 것이 좋을 것 같음.
 */
@SpringJUnitWebConfig(
    classes = {TodoRootConfig.class, TodoWebConfig.class})
class TodoControllerWebTestClientTests {

  @Autowired private WebApplicationContext wac;

  private WebTestClient client;

  @BeforeEach
  void setUp() {
    client = MockMvcWebTestClient.bindToApplicationContext(wac).build();
  }

  @Test
  void list() {
    client
        .get() //
        .uri("/todos")
        .exchange()
        .expectStatus()
        .isOk();
  }
}
