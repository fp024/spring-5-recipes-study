package org.fp024.study.spring5recipes.bank.restclient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.fp024.study.spring5recipes.bank.config.BankConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.client.MockRestServiceServer;

@SpringJUnitConfig(classes = {BankConfiguration.class})
class OpenIBANValidationClientTest {

  @Autowired private OpenIBANValidationClient client;

  private MockRestServiceServer mockRestServiceServer;

  @BeforeEach
  void init() {
    mockRestServiceServer = MockRestServiceServer.createServer(client);
  }

  @Test
  void validIban() {

    mockRestServiceServer
        .expect(
            requestTo(
                "https://openiban.com/validate/NL87TRIO0396451440?getBIC=true&validateBankCode=true"))
        .andRespond(
            withSuccess(
                new ClassPathResource("NL87TRIO0396451440-result.json"),
                MediaType.APPLICATION_JSON));

    IBANValidationResult result = client.validate("NL87TRIO0396451440");
    assertThat(result.isValid()).isTrue();
  }

  @Test
  void invalidIban() {

    mockRestServiceServer
        .expect(
            requestTo(
                "https://openiban.com/validate/NL28XXXX389242218?getBIC=true&validateBankCode=true"))
        .andRespond(
            withSuccess(
                new ClassPathResource("NL28XXXX389242218-result.json"),
                MediaType.APPLICATION_JSON));

    IBANValidationResult result = client.validate("NL28XXXX389242218");
    assertThat(result.isValid()).isFalse();
  }
}
