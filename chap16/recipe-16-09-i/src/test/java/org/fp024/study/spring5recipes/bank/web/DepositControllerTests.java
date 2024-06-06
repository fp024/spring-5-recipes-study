package org.fp024.study.spring5recipes.bank.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.fp024.study.spring5recipes.bank.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ModelMap;

@ExtendWith(MockitoExtension.class)
class DepositControllerTests {

  private static final String TEST_ACCOUNT_NO = "1234";
  private static final double TEST_AMOUNT = 50;
  @Mock private AccountService accountService;
  private DepositController depositController;

  @BeforeEach
  public void init() {
    depositController = new DepositController(accountService);
  }

  @Test
  void deposit() {
    // Setup
    Mockito.when(accountService.getBalance(TEST_ACCOUNT_NO)).thenReturn(150.0);
    ModelMap modelMap = new ModelMap();

    // Execute
    String viewName = depositController.deposit(TEST_ACCOUNT_NO, TEST_AMOUNT, modelMap);

    assertThat(viewName).isEqualTo("success");
    assertThat(modelMap)
        .containsEntry("accountNo", TEST_ACCOUNT_NO)
        .containsEntry("balance", 150.0);
  }
}
