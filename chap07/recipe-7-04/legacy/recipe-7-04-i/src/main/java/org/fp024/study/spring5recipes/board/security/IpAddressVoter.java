package org.fp024.study.spring5recipes.board.security;

import java.util.Collection;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Slf4j
public class IpAddressVoter implements AccessDecisionVoter<Object> {

  private static final String IP_PREFIX = "IP_";
  private static final String IP_LOCAL_HOST = "IP_LOCAL_HOST";

  @Override
  public boolean supports(ConfigAttribute attribute) {
    // WebExpressionConfigAttribute 타입으로 들어오는데..
    // attribute.getAttribute()의 반환값이 항상 null이였다.
    return (attribute.toString() != null) && attribute.toString().startsWith(IP_PREFIX);
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }

  @Override
  public int vote(
      Authentication authentication, Object object, Collection<ConfigAttribute> configList) {
    // 기본 테스트 시에는 getDetails()가 null 이 되어 실패하여 테스트 메서드 실행 상위에
    // WebAuthenticationDetails 를 생성해서 UsernamePasswordAuthenticationToken 에 설정해주는 코드를 넣었다.
    if (!(authentication.getDetails() instanceof WebAuthenticationDetails details)) {
      return ACCESS_DENIED;
    }

    String address = details.getRemoteAddress();

    int result = ACCESS_ABSTAIN;

    for (ConfigAttribute config : configList) {
      result = ACCESS_DENIED;
      LOGGER.info("### config: {}", config);
      LOGGER.info("### config.getAttribute(): {}", config.getAttribute()); // ✨ 여전히 null
      if (Objects.equals(IP_LOCAL_HOST, config.toString())) {
        if (address.equals("127.0.0.1") || address.equals("0:0:0:0:0:0:0:1")) {
          return ACCESS_GRANTED;
        }
      }
    }

    return result;
  }
}
