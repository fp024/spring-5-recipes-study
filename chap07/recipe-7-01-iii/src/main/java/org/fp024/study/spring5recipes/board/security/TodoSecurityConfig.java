package org.fp024.study.spring5recipes.board.security;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/*
  WebSecurityConfigurerAdapter가 지원 중단 되었고,
  org.springframework.security.web.SecurityFilterChain 빈을 사용하여
  HttpSecurity를 구성하거나 WebSecurityCustomizer 빈을 사용하여 WebSecurity를 구성하라고 함.

  WebSecurityConfigurerAdapter 상속 코드는 넣어두지 말자!
*/
@Configuration
@EnableWebSecurity
public class TodoSecurityConfig {
  @Bean
  UserDetailsService userDetailsService() {
    val user =
        User.builder() //
            .username("user")
            .password("{noop}user")
            .authorities("USER")
            .build();

    val admin =
        User.builder()
            .username("admin")
            .password("{noop}admin")
            .authorities("USER", "ADMIN")
            .build();

    return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
        (authz) ->
            authz
                .requestMatchers(
                    antMatcher("/webjars/**"), //
                    antMatcher("/resources/**"), //
                    antMatcher("/"),
                    antMatcher("/index"),
                    antMatcher("/favicon.ico"))
                .permitAll()
                .requestMatchers(antMatcher(HttpMethod.DELETE, "/todos/*"))
                .hasAuthority("ADMIN")
                .anyRequest()
                .authenticated());
    http.formLogin(withDefaults());
    http.csrf(withDefaults());
    return http.build();
  }
}
