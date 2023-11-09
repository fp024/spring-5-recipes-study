package org.fp024.study.spring5recipes.board.security;

import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class AccessChecker {
  public boolean hasLocalAccess(Authentication authentication) {
    boolean access = false;

    if (authentication.getDetails() instanceof WebAuthenticationDetails details) {
      val address = details.getRemoteAddress();
      return address.equals("127.0.0.1") || address.equals("0:0:0:0:0:0:0:1");
    }
    return access;
  }
}
