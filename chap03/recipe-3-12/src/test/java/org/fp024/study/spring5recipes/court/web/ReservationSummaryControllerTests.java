package org.fp024.study.spring5recipes.court.web;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitWebConfig(classes = {CourtConfiguration.class})
class ReservationSummaryControllerTests {
  private MockMvc mockMvc;

  @Autowired private WebApplicationContext context;

  @Autowired private ReservationService service;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    // 각 테스트마다 독립성을 위해 세션을 클리어해주자!
    service.resetData();
  }

  @Test
  void testReservationSummary_pdf() throws Exception {
    mockMvc
        .perform(
            get("/reservationSummary.pdf")
                .servletPath(
                    "/reservationSummary.pdf") // servletPath를 접근 URL과 동일하게 설정해줘야 헤더 검증이 제대로 된다.
                .queryParam("date", "2023-01-01"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("reservations", hasSize(2)))
        .andExpect(view().name("pdfReservationSummary"))
        .andExpect(
            header()
                .string(
                    "Content-Disposition",
                    "attachment; filename=ReservationSummary_2023_01_01.pdf"))
        .andDo(print());
  }

  @Test
  void testReservationSummary_xlsx() throws Exception {
    mockMvc
        .perform(
            get("/reservationSummary.xlsx")
                .servletPath(
                    "/reservationSummary.xlsx") // servletPath를 접근 URL과 동일하게 설정해줘야 헤더 검증이 제대로 된다.
                .queryParam("date", "2023-01-01"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("reservations", hasSize(2)))
        .andExpect(view().name("excelReservationSummary"))
        .andExpect(
            header()
                .string(
                    "Content-Disposition",
                    "attachment; filename=ReservationSummary_2023_01_01.xlsx"))
        .andDo(print());
  }

  @Test
  void testReservationSummary_html() throws Exception {
    mockMvc
        .perform(
            get("/reservationSummary")
                .servletPath(
                    "/reservationSummary") // servletPath를 접근 URL과 동일하게 설정해줘야 헤더 검증이 제대로 된다.
                .queryParam("date", "2023-01-01"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("reservations", hasSize(2)))
        .andExpect(view().name("reservationSummary"))
        .andExpect(header().doesNotExist("Content-Disposition"))
        .andDo(print());
  }

  @Test
  void testReservationSummary_notSupportExtension() throws Exception {
    mockMvc
        .perform(
            get("/reservationSummary.aaa")
                .servletPath(
                    "/reservationSummary.aaa") // servletPath를 접근 URL과 동일하게 설정해줘야 헤더 검증이 제대로 된다.
                .queryParam("date", "2023-01-01"))
        .andExpect(status().isOk())
        .andExpect(model().attributeDoesNotExist("reservations"))
        .andExpect(view().name("reservationSummaryError"))
        .andExpect(header().doesNotExist("Content-Disposition"))
        .andDo(print());
  }

  @Test
  void testReservationSummary_dateTimeParseException() throws Exception {
    mockMvc
        .perform(
            get("/reservationSummary.pdf")
                .servletPath(
                    "/reservationSummary.pdf") // servletPath를 접근 URL과 동일하게 설정해줘야 헤더 검증이 제대로 된다.
                .queryParam("date", "2023-a1-b1"))
        .andExpect(status().isOk())
        .andExpect(model().attributeDoesNotExist("reservations"))
        .andExpect(view().name("reservationSummaryError"))
        .andExpect(header().doesNotExist("Content-Disposition"))
        .andDo(print());
  }
}
