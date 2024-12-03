package org.fp024.study.spring5recipes.court.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import org.fp024.study.spring5recipes.court.config.CourtConfiguration;
import org.fp024.study.spring5recipes.court.domain.PeriodicReservation;
import org.fp024.study.spring5recipes.court.domain.Player;
import org.fp024.study.spring5recipes.court.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitWebConfig(classes = {CourtConfiguration.class})
class PeriodicReservationControllerTests {

  private MockMvc mockMvc;

  @Autowired MockHttpSession httpSession;

  @Autowired private WebApplicationContext context;

  @Autowired private ReservationService service;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    httpSession.clearAttributes();
    service.resetData();
  }

  /*
   코트 이름 입력 첫번째 폼 보여주기
  */
  @Test
  void reservationCourtForm_setup() throws Exception {
    mockMvc
        .perform(
            get("/periodicReservationForm") //
                .session(httpSession)) //
        .andExpect(status().isOk())
        .andExpect(view().name("reservationCourtForm"))
        .andDo(print());

    assertThat(httpSession.getAttribute("reservation")).isNotNull();
  }

  /*
   취소 폼: 취소 버튼 클릭
  */
  @Test
  void cancelForm() throws Exception {
    PeriodicReservation reservation = new PeriodicReservation();
    reservation.setCourtName("Tennis #1");
    reservation.setPlayer(new Player());
    httpSession.setAttribute("reservation", reservation);

    mockMvc
        .perform(
            post("/periodicReservationForm") //
                .param("_cancel", "Previous")
                .param("_page", "1")
                .session(httpSession)) //
        .andExpect(status().isOk())
        .andExpect(view().name("reservationTimeForm"))
        .andDo(print());

    assertThat(httpSession.getAttribute("reservation")).isNotNull();
  }

  /*
   1st Form: 코트 이름 입력 폼에서 submit
  */
  @Test
  void reservationCourtForm_submit() throws Exception {
    PeriodicReservation reservation = new PeriodicReservation();
    reservation.setCourtName("Tennis #1");
    reservation.setPlayer(new Player());
    httpSession.setAttribute("reservation", reservation);

    mockMvc
        .perform(
            post("/periodicReservationForm") //
                .param("_target1", "Next")
                .param("_page", "0")
                .session(httpSession)) //
        .andExpect(status().isOk())
        .andExpect(view().name("reservationTimeForm"))
        .andDo(print());

    assertThat(httpSession.getAttribute("reservation")).isNotNull();
  }

  /*
   2nd Form: 예약 일자 시간 입력 폼에서 submit
  */
  @Test
  void reservationTimeForm_submit() throws Exception {
    PeriodicReservation reservation = new PeriodicReservation();
    reservation.setCourtName("Tennis #1");
    reservation.setFromDate(LocalDate.of(2023, 9, 1));
    reservation.setToDate(LocalDate.of(2023, 9, 30));
    reservation.setPeriod(7); // 1(매일) 또는 7(주마다)이다
    reservation.setHour(13);
    reservation.setPlayer(new Player());
    httpSession.setAttribute("reservation", reservation);

    mockMvc
        .perform(
            post("/periodicReservationForm") //
                .param("_target2", "Next")
                .param("_page", "1")
                .session(httpSession)) //
        .andExpect(status().isOk())
        .andExpect(view().name("reservationPlayerForm"))
        .andDo(print());

    assertThat(httpSession.getAttribute("reservation")).isNotNull();
  }

  /*
    3rd Form: 플레이어 정보 입력 폼에서 submit
  */
  @Test
  void reservationPlayerForm_submit() throws Exception {
    PeriodicReservation reservation = new PeriodicReservation();
    reservation.setCourtName("Tennis #1");
    reservation.setFromDate(LocalDate.of(2023, 9, 1));
    reservation.setToDate(LocalDate.of(2023, 9, 30));
    reservation.setPeriod(7); // 1(매일) 또는 7(주마다)이다
    reservation.setHour(13);

    reservation.setPlayer(new Player("user00", "01000000000"));
    httpSession.setAttribute("reservation", reservation);

    mockMvc
        .perform(
            post("/periodicReservationForm") //
                .param("_finish", "Next")
                .param("_page", "2")
                .session(httpSession)) //
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("reservationSuccess"))
        .andDo(print());

    // 성공적으로 등록한 경우 세션을 클리어하기 때문에 reservation 은 더이상 세션에 없다.
    assertThat(httpSession.getAttribute("reservation")).isNull();
  }
}
