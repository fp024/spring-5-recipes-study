package org.fp024.study.spring5recipes.court.web;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.fp024.study.spring5recipes.court.config.CourtConfiguration;
import org.fp024.study.spring5recipes.court.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@SpringJUnitWebConfig(classes = {CourtConfiguration.class})
class ReservationQueryControllerTest {
  private MockMvc mockMvc;

  @Autowired private ReservationService reservationService;

  @Autowired private MeasurementInterceptor measurementInterceptor;

  @Autowired private LocaleChangeInterceptor localeChangeInterceptor;

  @Autowired private ExtensionInterceptor extensionInterceptor;

  @Autowired private LocaleResolver localeResolver;

  @BeforeEach
  void setUp() {
    this.mockMvc =
        MockMvcBuilders.standaloneSetup(new ReservationQueryController(reservationService))
            .addInterceptors(measurementInterceptor, localeChangeInterceptor)
            .addMappedInterceptors(new String[] {"/reservationSummary*"}, extensionInterceptor)
            .setLocaleResolver(localeResolver)
            .build();
  }

  @Test
  void testSetupForm() throws Exception {
    mockMvc
        .perform(get("/reservationQuery/?language=en")) //
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("handlingTime"))
        .andExpect(view().name("reservationQuery"))
        .andExpect(cookie().value("language", "en"))
        .andDo(print());
  }

  @Test
  void testSubmitForm() throws Exception {
    mockMvc
        .perform(
            post("/reservationQuery/") //
                .param("courtName", "Tennis #1"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("reservations", "handlingTime"))
        .andExpect(model().attribute("reservations", hasSize(1)))
        .andExpect(view().name("reservationQuery"))
        .andDo(print());
  }

  @Test
  void testReservationSummary() throws Exception {
    mockMvc
        .perform(
            get("/reservationSummary.html")
                .servletPath(
                    "/reservationSummary.html") // servletPath를 접근 URL과 동일하게 설정해줘야 헤더 검증이 제대로 된다.
                .queryParam("date", "2023-01-01"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("reservations", hasSize(2)))
        .andExpect(view().name("reservationSummary"))
        .andExpect(
            header()
                .string(
                    "Content-Disposition",
                    "attachment; filename=ReservationSummary_2023_01_01.html"))
        .andDo(print());
  }
}
