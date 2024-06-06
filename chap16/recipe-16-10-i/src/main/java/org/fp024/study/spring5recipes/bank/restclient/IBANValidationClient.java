package org.fp024.study.spring5recipes.bank.restclient;

public interface IBANValidationClient {
  IBANValidationResult validate(String iban);
}
