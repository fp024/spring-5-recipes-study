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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitWebConfig(classes = {RootConfiguration.class, ServletConfiguration.class})
class FeedControllerTests {
  private MockMvc mockMvc;

  @Autowired private WebApplicationContext context;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  void testGetAtomFeed() throws Exception {
    mockMvc
        .perform(get("/atomfeed")) //
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().contentType(MediaType.APPLICATION_ATOM_XML))
        .andExpect(xpath("/feed/title").string("Grand Slam Tournaments"))
        .andExpect(xpath("/feed/id").string("tag:tennis.org"));
  }

  @Test
  void testGetRssFeed() throws Exception {
    mockMvc
        .perform(get("/rssfeed")) //
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().contentType(MediaType.APPLICATION_RSS_XML))
        .andExpect(xpath("/rss/channel/title").string("World Soccer Tournaments"))
        .andExpect(xpath("/rss/channel/link").string("www.fifa.com"));
  }

  @Test
  void testGetJSON() throws Exception {
    mockMvc
        .perform(get("/jsontournament")) //
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.feedContent[0].name").value("World Cup"))
        .andExpect(jsonPath("$.feedContent[0].link").value("www.fifa.com/worldcup/"));
  }
}
