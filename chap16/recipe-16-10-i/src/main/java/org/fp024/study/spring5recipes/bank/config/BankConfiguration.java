package org.fp024.study.spring5recipes.bank.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"org.fp024.study.spring5recipes.bank.restclient"})
public class BankConfiguration {}
