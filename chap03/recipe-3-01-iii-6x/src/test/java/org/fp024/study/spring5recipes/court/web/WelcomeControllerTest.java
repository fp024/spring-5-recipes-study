package org.fp024.study.spring5recipes.court.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.fp024.study.spring5recipes.court.service.config.ServiceConfiguration;
import org.fp024.study.spring5recipes.court.web.config.WebConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringJUnitWebConfig(classes = {ServiceConfiguration.class, WebConfiguration.class})
class WelcomeControllerTest {
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(new WelcomeController()).build();
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
        .perform(get("/welcome/")) //
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("today"))
        .andExpect(view().name("welcome"));
  }
}
