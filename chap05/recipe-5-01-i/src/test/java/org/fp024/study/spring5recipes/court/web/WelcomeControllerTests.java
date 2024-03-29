package org.fp024.study.spring5recipes.court.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
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
    // 테스트 환경에서 /welcome으로 요청하면 다음 예외가 발생한다. (서버를 실행시켜서 요청할 때는 문제가 없음.)
    // javax.servlet.ServletException: Circular view path [welcome]: would dispatch back to the
    // current handler URL [/welcome] again.
    // 1. get 요청 주소와 view 반환 이름을 다르게 하거나
    // 2. Thymeleaf를 사용하면 되긴하는데...
    // 일단 테스트 할 때만 /를 끝에 하나 더 붙여보자..

    mockMvc
        .perform(get("/welcome/?language=ko")) //
        .andExpect(status().isOk())
        .andExpect(view().name("welcome"))
        .andExpect(cookie().value("language", "ko"))
        .andDo(print());
  }
}
