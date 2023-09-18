package org.fp024.study.spring5recipes.court.web.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class NotSupportExtensionException extends IllegalArgumentException {
  private final String notSupportExtension;

  public String getMessage() {
    return "Not Support Extension: %s".formatted(notSupportExtension);
  }
}
