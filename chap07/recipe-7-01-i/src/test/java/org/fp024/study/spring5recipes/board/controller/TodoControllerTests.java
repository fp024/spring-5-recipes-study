package org.fp024.study.spring5recipes.board.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.fp024.study.spring5recipes.board.config.TodoWebConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitWebConfig(classes = {TodoWebConfig.class})
class TodoControllerTests {

  private MockMvc mockMvc;

  @Autowired private WebApplicationContext context;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
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
        .perform(post("/todos").param("description", "7장 예제 빨리하자! 😅").param("completed", "off"))
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(model().attributeExists("todo"))
        .andExpect(redirectedUrl("/todos"));
  }

  @Transactional
  @Test
  void complete() throws Exception {
    mockMvc
        .perform(put("/todos/{todoId}/completed", 1))
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/todos"));
  }

  @Transactional
  @Test
  void delete() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.delete("/todos/{todoId}", 2))
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/todos"));
  }
}
