package org.fp024.study.spring5recipes.board;

import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

/*
  로그인 유저를 data.sql에서 쿼리로 넣었었는데...
  캐시가 되는지 확실하게 보려면, 코드 반복문으로 넣는게 낫겠다.
*/
@RequiredArgsConstructor
@Component
class SecurityUserInitializer {

  private final UserDetailsManager userDetailsManager;

  private final PasswordEncoder passwordEncoder;

  @PostConstruct
  public void setup() {
    // admin 추가
    UserDetails admin =
        User.withUsername("admin")
            .authorities("ADMIN")
            .password(passwordEncoder.encode("admin"))
            .build();

    userDetailsManager.createUser(admin);

    // user?? 추가
    IntStream.rangeClosed(1, 9)
        .forEach(
            i -> {
              UserDetails user =
                  User.withUsername("user%02d".formatted(i))
                      .authorities("USER")
                      .password(passwordEncoder.encode("user%02d".formatted(i)))
                      .build();
              userDetailsManager.createUser(user);
            });
  }
}
