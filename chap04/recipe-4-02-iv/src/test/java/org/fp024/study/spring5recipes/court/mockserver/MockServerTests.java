package org.fp024.study.spring5recipes.court.mockserver;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.court.config.RootConfiguration;
import org.fp024.study.spring5recipes.court.config.ServletConfiguration;
import org.fp024.study.spring5recipes.court.domain.Members;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

  // 4-03-i 예제에 해당
  @Test
  void testMembersXml_ReturnType_String() {
    String result = restTemplate.getForObject("/members.xml", String.class);
    LOGGER.info("###### Response #####%n%s".formatted(result));
    assertThat(result).isNotEmpty();
  }

  // 4-03-i 예제에 해당
  @Test
  void testMembersJson_ReturnType_String() {
    String result = restTemplate.getForObject("/members.json", String.class);
    LOGGER.info("###### Response #####%n%s".formatted(result));
    assertThat(result).isNotEmpty();
  }

  // 4-03-ii 예제에 해당
  // 💡 따로 Accept 헤더를 설정하지 않았을 때, JSON으로 반환
  @Test
  void testMemberJson_ReturnType_String() {
    Map<String, String> params = Map.of("memberId", "1");
    String result = restTemplate.getForObject("/member/{memberId}", String.class, params);
    LOGGER.info("###### Response #####%n%s".formatted(result));
    assertThat(result).isNotEmpty();
  }

  // 4-03-ii 예제에 해당
  // Accept 헤더에 명시적으로 application/xml 으로 설정
  @Test
  void testMemberXml_ReturnType_String() {
    RequestEntity<Void> requestEntity =
        RequestEntity.get("/member/{memberId}", 1)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE)
            .build();
    ResponseEntity<String> result = restTemplate.exchange(requestEntity, String.class);
    LOGGER.info("###### Response #####%n%s".formatted(result.getBody()));
    assertThat(result.getBody()).isNotEmpty();
  }

  // 4-03-iii 예제에 해당 - 도메인으로 매핑해서 결과를 받음
  @Test
  void testMembersJson_ReturnType_Members() {
    Members members = restTemplate.getForObject("/members.json", Members.class);
    LOGGER.info("###### Response #####%n%s".formatted(members));
    assertThat(members.getMembers()).isNotNull();
  }

  // 4-03-iii 예제에 해당 - 도메인으로 매핑해서 결과를 받음
  @Test
  void testMembersXml_ReturnType_Members() {
    Members members = restTemplate.getForObject("/members.xml", Members.class);
    LOGGER.info("###### Response #####%n%s".formatted(members));
    assertThat(members.getMembers()).isNotNull();
  }
}
