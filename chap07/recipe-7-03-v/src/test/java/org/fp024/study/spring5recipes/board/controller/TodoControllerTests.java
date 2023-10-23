package org.fp024.study.spring5recipes.board.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.fp024.study.spring5recipes.board.config.TodoRootConfig;
import org.fp024.study.spring5recipes.board.config.TodoWebConfig;
import org.fp024.study.spring5recipes.board.security.TodoSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/*
 프로젝트 자체가 내장 LDAP 서버가 같이 동작하는데,
 해당 LDAP 서버 포트를 점유하고 있으면 테스트가 실패한다.

 테스트 시는 랜덤으로 바꾸면 좋을 것 같긴한데...
*/
@SpringJUnitWebConfig(
    classes = {TodoRootConfig.class, TodoWebConfig.class, TodoSecurityConfig.class})
@WithMockUser(
    value = "user00",
    authorities = {"USER"})
class TodoControllerTests {

  private MockMvc mockMvc;

  @Autowired private WebApplicationContext context;

  @BeforeEach
  void setUp() {
    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(context) //
            .apply(springSecurity())
            .build();
  }

  @Test
  void list() throws Exception {
    mockMvc
        .perform(get("/todos"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("todos"))
        .andExpect(view().name("todos"));
  }

  @Transactional
  @Test
  void create() throws Exception {
    mockMvc
        .perform(get("/todos/new"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("todo"))
        .andExpect(view().name("todo-create"));
  }

  @Transactional
  @Test
  void newMessage() throws Exception {
    mockMvc
        .perform(
            post("/todos") //
                .param("description", "7장 예제 빨리하자! 😅")
                .with(csrf())
            // .param("completed", "off") // 체크가 안되어있으면 파라미터 자체가 전달이 안됨.
            // 체크가 된 경우는 별도 value 값이 설정되지 않았다면 "on" 전달
            )
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(model().attributeExists("todo"))
        .andExpect(redirectedUrl("/todos"));
  }

  @Transactional
  @Test
  void complete() throws Exception {
    mockMvc
        .perform(
            put("/todos/{todoId}/completed", 1) //
                .with(csrf()))
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/todos"));
  }

  @Transactional
  @Test
  @WithMockUser(
      value = "admin",
      authorities = {"ADMIN"})
  void delete() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/todos/{todoId}", 2) //
                .with(csrf()))
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/todos"));
  }
}
