package org.fp024.study.spring5recipes.court.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.fp024.study.spring5recipes.court.config.CourtConfiguration;
import org.fp024.study.spring5recipes.court.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringJUnitWebConfig(classes = {CourtConfiguration.class})
class MemberControllerTest {
  private MockMvc mockMvc;

  @Autowired private MemberService memberService;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(new MemberController(memberService)).build();
  }

  @Test
  void testAddMember() throws Exception {
    mockMvc
        .perform(
            post("/member/add")
                .param("name", "홍길동")
                .param("phone", "010-0000-0000")
                .param("email", "a@a.org")) //
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/member/list"));
  }

  @Test
  void testList() throws Exception {
    mockMvc
        .perform(get("/member/list"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("memberList"))
        .andExpect(view().name("memberList"));
  }

  @Test
  void testRemoveMember() throws Exception {
    mockMvc
        .perform(
            post("/member/remove") //
                .param("memberName", "홍길동"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/member/list"));
  }
}
