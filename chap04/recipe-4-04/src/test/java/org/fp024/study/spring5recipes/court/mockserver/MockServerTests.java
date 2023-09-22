package org.fp024.study.spring5recipes.court.mockserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.court.config.RootConfiguration;
import org.fp024.study.spring5recipes.court.config.ServletConfiguration;
import org.fp024.study.spring5recipes.court.feeds.TournamentContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

/*
  `레시피 4-03-i ~ iii`는 MockServer를 실행시켜서 그것에 대고 RestTemplate을 사용해서 호출하는 식으로 하면 되겠다.
   스프링 부트 였다면 랜덤 포트에 서버 실행시켜서 테스트 할 수도 있지만,
   이 프로젝트는 일반 Spring 프로젝트이니 이렇게 먼저 해보자!

   결국 이 테스트는 RestTemplate 사용법 익히는 테스트!
*/
@Slf4j
@SpringJUnitWebConfig(classes = {RootConfiguration.class, ServletConfiguration.class})
class MockServerTests {
  @Autowired private WebApplicationContext wac;
  private RestTemplate restTemplate;

  @BeforeEach
  void setup() {
    MockMvc mockMvc =
        MockMvcBuilders.webAppContextSetup(this.wac).alwaysExpect(status().isOk()).build();

    this.restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
  }

  @Test
  void testAtomFeed() {
    String result = restTemplate.getForObject("/atomfeed", String.class);
    LOGGER.info("###### Response #####%n%s".formatted(result));
    assertThat(result).isNotEmpty();
  }

  @Test
  void testRssFeed() {
    String result = restTemplate.getForObject("/rssfeed", String.class);
    LOGGER.info("###### Response #####%n%s".formatted(result));
    assertThat(result).isNotEmpty();
  }

  // 클라이언트 프로그램에서 역직렬화를 위한 도메인 클래스를 따로 만듬.
  @Test
  void testRssGetJSON_Use_Client_Domain() {
    ResponseEntity<TournamentContents> response =
        restTemplate.exchange(
            RequestEntity.get("/jsontournament").build(), TournamentContents.class);
    TournamentContents result = response.getBody();

    LOGGER.info("###### Response #####%n%s".formatted(result));
    assertThat(result.getFeedContent()).isNotEmpty();
  }

  // 생성측 도메인을 그대로 사용
  @Test
  void testRssGetJSON_Use_JsonCreator() {
    ParameterizedTypeReference<Map<String, List<TournamentContent>>> paramRef =
        new ParameterizedTypeReference<>() {};

    ResponseEntity<Map<String, List<TournamentContent>>> response =
        restTemplate.exchange(RequestEntity.get("/jsontournament").build(), paramRef);
    Map<String, List<TournamentContent>> result = response.getBody();

    LOGGER.info("###### Response #####%n%s".formatted(result));
    assertThat(result.get("feedContent")).isNotEmpty();
  }
}
