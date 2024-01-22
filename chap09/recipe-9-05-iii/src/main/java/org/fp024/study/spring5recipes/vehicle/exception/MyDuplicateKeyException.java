package org.fp024.study.spring5recipes.vehicle.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class MyDuplicateKeyException extends DataIntegrityViolationException {
  public MyDuplicateKeyException(String msg) {
    super(msg);
  }

  public MyDuplicateKeyException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
