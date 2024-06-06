package org.fp024.study.spring5recipes.bank.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.fp024.study.spring5recipes.bank.config.BankConfiguration;
import org.fp024.study.spring5recipes.bank.service.AccountService;
import org.fp024.study.spring5recipes.bank.web.config.BankWebConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitConfig(classes = {BankWebConfiguration.class, BankConfiguration.class})
@WebAppConfiguration
@EnabledIf("#{systemProperties['spring.profiles.active'] != 'in-mem'}")
@Transactional
@Sql(scripts = "classpath:/bank.sql")
class DepositControllerJUnitContextTests {

  private static final String ACCOUNT_PARAM = "accountNo";
  private static final String AMOUNT_PARAM = "amount";

  private static final String TEST_ACCOUNT_NO = "1234";
  private static final String TEST_AMOUNT = "50.0";

  @Autowired private WebApplicationContext webApplicationContext;

  @Autowired private AccountService accountService;

  private MockMvc mockMvc;

  @BeforeEach
  void init() {
    accountService.createAccount(TEST_ACCOUNT_NO);
    accountService.deposit(TEST_ACCOUNT_NO, 100);
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  void deposit() throws Exception {
    mockMvc
        .perform(
            get("/deposit.do")
                .param(ACCOUNT_PARAM, TEST_ACCOUNT_NO)
                .param(AMOUNT_PARAM, TEST_AMOUNT))
        .andDo(print())
        .andExpect(forwardedUrl("/WEB-INF/views/success.jsp"))
        .andExpect(status().isOk());
  }
}
