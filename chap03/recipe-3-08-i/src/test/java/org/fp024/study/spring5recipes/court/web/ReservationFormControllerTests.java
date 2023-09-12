package org.fp024.study.spring5recipes.court.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import org.fp024.study.spring5recipes.court.config.CourtConfiguration;
import org.fp024.study.spring5recipes.court.domain.Player;
import org.fp024.study.spring5recipes.court.domain.Reservation;
import org.fp024.study.spring5recipes.court.domain.ReservationValidator;
import org.fp024.study.spring5recipes.court.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@SpringJUnitWebConfig(classes = {CourtConfiguration.class})
class ReservationFormControllerTests {
  private MockMvc mockMvc;

  @Autowired MockHttpSession httpSession;

  @Autowired private ReservationService reservationService;

  @Autowired private MeasurementInterceptor measurementInterceptor;

  @Autowired private LocaleChangeInterceptor localeChangeInterceptor;

  @Autowired private ExtensionInterceptor extensionInterceptor;

  @Autowired private LocaleResolver localeResolver;

  @Autowired private ReservationValidator validator;

  @Autowired private ReservationService service;

  @BeforeEach
  void setUp() {
    this.mockMvc =
        MockMvcBuilders.standaloneSetup(
                new ReservationFormController(reservationService, validator))
            .addInterceptors(measurementInterceptor, localeChangeInterceptor)
            .addMappedInterceptors(new String[] {"/reservationSummary*"}, extensionInterceptor)
            .setLocaleResolver(localeResolver)
            .build();

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
        .andExpect(model().attributeExists("handlingTime"))
        .andExpect(view().name("reservationForm"))
        .andExpect(cookie().value("language", "en"))
        .andDo(print());

    Reservation reservation = (Reservation) httpSession.getAttribute("reservation");
    assertThat(reservation).isNotNull();
    assertThat(reservation.getPlayer()).isNotNull();
  }

  @Test
  void submitForm() throws Exception {
    Reservation reservation = new Reservation();
    reservation.setPlayer(new Player(null, null));
    httpSession.setAttribute("reservation", reservation);

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
        .andExpect(status().isOk())
        .andExpect(view().name("reservationForm"))
        .andDo(print());
  }
}
