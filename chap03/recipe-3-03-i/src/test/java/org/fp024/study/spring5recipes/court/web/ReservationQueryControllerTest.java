package org.fp024.study.spring5recipes.court.web;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.web.servlet.HandlerInterceptor;

@SpringJUnitWebConfig(classes = {CourtConfiguration.class})
class ReservationQueryControllerTest {
  private MockMvc mockMvc;

  @Autowired private ReservationService reservationService;

  @Autowired private HandlerInterceptor measurementInterceptor;

  @BeforeEach
  void setUp() {
    this.mockMvc =
        MockMvcBuilders.standaloneSetup(new ReservationQueryController(reservationService))
            .addInterceptors(measurementInterceptor)
            .build();
  }

  @Test
  void testSetupForm() throws Exception {
    mockMvc
        .perform(get("/reservationQuery/")) //
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("handlingTime"))
        .andExpect(view().name("reservationQuery"));
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
        .andReturn();
  }
}
