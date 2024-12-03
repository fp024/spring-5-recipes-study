package org.fp024.study.spring5recipes.court.webclient;

import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.court.config.CourtConfiguration;
import org.fp024.study.spring5recipes.court.domain.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@SpringJUnitWebConfig(classes = {CourtConfiguration.class})
class WebTestClientTests {
  @Autowired WebApplicationContext wac;

  WebTestClient client;

  @BeforeEach
  void setUp() {
    client = MockMvcWebTestClient.bindToApplicationContext(this.wac).build();
  }

  /*
   ...
   06:38:30.528 [Test worker] DEBUG org.springframework.web.context.request.async.WebAsyncManager - Started async request
   06:38:34.530 [mvcTaskExecutor-1] DEBUG org.springframework.web.context.request.async.WebAsyncManager - Async result set, dispatch to /reservationQuery/
   06:38:34.534 [Test worker] DEBUG org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter - Resume with async result []
   ...
   이후 결과가 한꺼번에 출력됨

   콘솔로그를 보면 비동기 요청을 시작하고 비동기 스레드가 아마도 완료 했다는 로그를 출력하기까지 4초 정도의 대기가 있다.
   테스트 환경에서는 어쩔 수 없는 것이지? 여기까지만 보자 😅
  */

  @Test
  void testFind() {
    client
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path("/reservationQuery") //
                    .queryParam("courtName", "Tennis #1")
                    .build())
        .accept(MediaType.TEXT_EVENT_STREAM)
        .exchange()
        .returnResult(Reservation.class) // FluxExchangeResult<Reservation> 객체를 얻음
        .getResponseBody() // 응답 본문을 Flux<Reservation>로 얻음
        .doOnNext(reservation -> LOGGER.info(reservation.toString())) // 각 아이템이 도착할 때마다 로그 출력
        .subscribe(); // Flux를 구독하여 아이템을 처리
  }
}
