package org.fp024.study.spring5recipes.court.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.fp024.study.spring5recipes.court.config.CourtConfiguration;
import org.fp024.study.spring5recipes.court.domain.Player;
import org.fp024.study.spring5recipes.court.domain.Reservation;
import org.fp024.study.spring5recipes.court.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitWebConfig(classes = {CourtConfiguration.class})
class ReservationFormControllerTests {
  private MockMvc mockMvc;

  @Autowired MockHttpSession httpSession;

  @Autowired private WebApplicationContext context;

  @Autowired private ReservationService service;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    // 각 테스트마다 독립성을 위해 세션을 클리어해주자!
    httpSession.clearAttributes();
    service.resetData();
  }

  @Test
  void setupForm() throws Exception {
    mockMvc
        .perform(
            get("/reservationForm/") //
                .queryParam("language", "en")
                .session(httpSession)) //
        .andExpect(status().isOk())
        .andExpect(view().name("reservationForm"))
        .andExpect(cookie().value("language", "en"))
        .andDo(print());

    Reservation reservation = (Reservation) httpSession.getAttribute("reservation");
    assertThat(reservation).isNotNull();
    assertThat(reservation.getPlayer()).isNotNull();
  }

  // 비동기 컨트롤러 메서드
  @Test
  void submitForm() throws Exception {
    Reservation reservation = new Reservation();
    reservation.setPlayer(new Player(null, null));
    httpSession.setAttribute("reservation", reservation);

    MvcResult mvcResult =
        mockMvc
            .perform(
                post("/reservationForm/") //
                    .param("courtName", "Tennis #1")
                    .param("date", "2008-01-14")
                    .param("hour", "15")
                    .param("player.name", "user00")
                    .param("player.phone", "N/A")
                    .param("sportType", "1")
                    .session(httpSession))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
    // Async 코드 부분이 처리될 때까지 200 응답으로 끝나지만, view등이 null이다.
    // 아마도 브라우저 화면상에서는 변화가 없는 상태일 것 같다.

    mockMvc
        .perform(asyncDispatch(mvcResult))
        .andExpect(status().isFound())
        // 인터셉터에서 modelAndView에 설정한 속성이 URL에 남는듯.
        .andExpect(redirectedUrlPattern("reservationSuccess?handlingTime=*"))
        .andDo(print());
    // 이후 Aync 처리가 되면 이때 리다이렉트 함.

  }

  @DisplayName("중복 예약 시간 테스트")
  @Test
  void submitForm_Failure() throws Exception {
    Reservation reservation = new Reservation();
    reservation.setPlayer(new Player(null, null));
    httpSession.setAttribute("reservation", reservation);

    MvcResult mvcResult =
        mockMvc
            .perform(
                post("/reservationForm/") //
                    .param("courtName", "Tennis #1")
                    .param("date", "2008-01-14")
                    .param("hour", "16")
                    .param("player.name", "user00")
                    .param("player.phone", "N/A")
                    .param("sportType", "1")
                    .session(httpSession))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

    mockMvc
        .perform(asyncDispatch(mvcResult))
        .andDo(print())
        .andExpect(view().name("reservationNotAvailable"))
        .andExpect(model().attributeExists("exception"))
        .andDo(print());
  }

  @DisplayName("JSR-303 Validator 어노테이션 테스트 - 코트 이름 검증 실패")
  @Test
  void submitForm_JSR303_validator() throws Exception {
    Reservation reservation = new Reservation();
    reservation.setPlayer(new Player(null, null));
    httpSession.setAttribute("reservation", reservation);

    MvcResult mvcResult =
        mockMvc
            .perform(
                post("/reservationForm/") //
                    // .param("courtName", "") // 코트 이름 검증 실패
                    .param("date", "2008-01-14")
                    .param("hour", "16")
                    .param("player.name", "")
                    .param("player.phone", "N/A")
                    .param("sportType", "1")
                    .queryParam("language", "ko")
                    .session(httpSession))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(cookie().value("language", "ko"))
            .andReturn();

    mockMvc
        .perform(asyncDispatch(mvcResult))
        .andDo(print())
        .andExpect(view().name("reservationForm"))
        .andExpect(model().attributeHasFieldErrors("reservation", "courtName"))
        .andExpect(model().attributeHasFieldErrorCode("reservation", "player.name", "NotEmpty"))
        .andDo(print());
  }
}
