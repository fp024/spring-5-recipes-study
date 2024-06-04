package org.fp024.study.spring5recipes.bank.config;

import org.fp024.study.spring5recipes.bank.dao.InMemoryAccountDao;
import org.fp024.study.spring5recipes.bank.service.AccountServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankConfiguration {

  @Bean
  InMemoryAccountDao accountDao() {
    return new InMemoryAccountDao();
  }

  @Bean
  AccountServiceImpl accountService() {
    return new AccountServiceImpl(accountDao());
  }
}
