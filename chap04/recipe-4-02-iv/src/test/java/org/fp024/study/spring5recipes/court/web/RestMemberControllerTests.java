package org.fp024.study.spring5recipes.court.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import org.fp024.study.spring5recipes.court.config.RootConfiguration;
import org.fp024.study.spring5recipes.court.config.ServletConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
        .perform(get("/members.xml")) //
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().contentType(MediaType.APPLICATION_XML))
        .andExpect(xpath("/members/member[3]/name").string("Jane Doe"));
  }

  /*
   Members를 바로 @ResponseBody로 리턴하기 때문에 members를 두번 깜싸는 모양이 나오질 않는다.
   261쪽의 결과 모양이 잘못된 것 같다. members가 한번만 감싸는 모양이 되야함.
  */
  @ParameterizedTest
  @ValueSource(strings = {"/members", "/members.json"})
  void testGetRestMembersJson(String url) throws Exception {
    mockMvc
        .perform(get(url)) //
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.members[0].name").value("Marten Deinum"));
  }

  @Test
  void testGetMember() throws Exception {
    mockMvc
        .perform(get("/member/%s".formatted("1"))) //
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name").value("Marten Deinum"));
  }

  @Test
  void testGetMember_NotFound_Member() throws Exception {
    mockMvc
        .perform(get("/member/%s".formatted("5"))) //
        .andDo(print())
        .andExpect(status().isNotFound());
  }
}
