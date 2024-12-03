package org.fp024.study.spring5recipes.court.web;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.fp024.study.spring5recipes.court.config.CourtConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitWebConfig(classes = {CourtConfiguration.class})
class WelcomeControllerTests {
  private MockMvc mockMvc;

  @Autowired private WebApplicationContext context;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  void testWelcome() throws Exception {

    mockMvc
        .perform(get("/welcome?language=ko")) //
        .andExpect(status().isOk())
        .andExpect(view().name("welcome"))
        .andExpect(cookie().value("language", "ko"))
        .andExpect(model().attribute("handlingTime", greaterThan(0L)))
        .andDo(print());
  }
}
