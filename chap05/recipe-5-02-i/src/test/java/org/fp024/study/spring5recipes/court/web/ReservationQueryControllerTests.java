package org.fp024.study.spring5recipes.court.web;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.fp024.study.spring5recipes.court.config.CourtConfiguration;
import org.fp024.study.spring5recipes.court.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@SpringJUnitWebConfig(classes = {CourtConfiguration.class})
class ReservationQueryControllerTests {
  private MockMvc mockMvc;

  @Autowired private WebApplicationContext context;

  @Autowired private ReservationService service;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    service.resetData();
  }

  @Test
  void testSetupForm() throws Exception {
    mockMvc
        .perform(get("/reservationQuery/?language=en")) //
        .andExpect(status().isOk())
        .andExpect(view().name("reservationQuery"))
        .andExpect(cookie().value("language", "en"))
        .andDo(print());
  }

  // 비동기 컨트롤러 메서드 테스트
  @Test
  void testSubmitForm() throws Exception {
    // 200 응답만 확인, 비동기라서 이때는 아직 모델에 값들이 없다.
    MvcResult mvcResult =
        mockMvc
            .perform(
                post("/reservationQuery/") //
                    .param("courtName", "Tennis #1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

    // mvc 결과로 async 처리 확인
    mockMvc
        .perform(asyncDispatch(mvcResult))
        .andDo(print())
        .andExpect(model().attributeExists("reservations"))
        .andExpect(model().attribute("reservations", hasSize(1)))
        .andExpect(view().name("reservationQuery"))
        .andDo(print());
  }

  // ResponseBodyEmitter 사용한 컨트롤러 메서드
  @Test
  void testFind() throws Exception {
    MvcResult mvcResult =
        mockMvc
            .perform(
                get("/reservationQuery/") //
                    .queryParam("courtName", "Tennis #1")) //
            .andDo(print())
            .andExpect(status().is(HttpStatus.I_AM_A_TEAPOT.value()))
            .andReturn();

    mockMvc
        .perform(asyncDispatch(mvcResult)) //
        .andDo(print());

    String responseBody = mvcResult.getResponse().getContentAsString();

    // 첫번째 스트림 응답만 가져옴. 어쩔 수 없음. 😂
    LOGGER.info("### responseBody: {}", responseBody);

    Assertions.assertThat(responseBody) //
        .isNotEmpty()
        .doesNotHaveToString("Tennis #2");
  }
}
