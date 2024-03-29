package org.fp024.study.spring5recipes.court.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import org.fp024.study.spring5recipes.court.config.RootConfiguration;
import org.fp024.study.spring5recipes.court.config.ServletConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitWebConfig(classes = {RootConfiguration.class, ServletConfiguration.class})
class RestMemberControllerTests {
  private MockMvc mockMvc;

  @Autowired private WebApplicationContext context;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  void testGetRestMembers() throws Exception {
    mockMvc
        .perform(get("/members")) //
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_XML))
        .andExpect(xpath("/members/member[3]/name").string("Jane Doe"));
  }

  @Test
  void testGetMember() throws Exception {
    mockMvc
        .perform(get("/member/%s".formatted("1"))) //
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_XML))
        .andExpect(xpath("/member/name").string("Marten Deinum"));
  }

  @Test
  void testGetMember_NotFound_Member() throws Exception {
    mockMvc
        .perform(get("/member/%s".formatted("5"))) //
        .andDo(print())
        .andExpect(status().isNotFound());
  }
}
