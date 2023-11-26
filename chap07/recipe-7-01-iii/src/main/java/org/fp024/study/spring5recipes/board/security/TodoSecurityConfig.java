package org.fp024.study.spring5recipes.board.security;

import static org.springframework.security.config.Customizer.withDefaults;

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
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

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
  HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
    return new HandlerMappingIntrospector();
  }

  @Bean
  MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
    return new MvcRequestMatcher.Builder(introspector);
  }

  // https://github.com/spring-projects/spring-security/issues/13602#issuecomment-1816499124
  // 내용대로 설정을 변경, 이러면 확실히 `MvcRequestMatcher`를 사용하는 상태가 되긴함.
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc)
      throws Exception {
    http.authorizeHttpRequests(
        (authz) ->
            authz
                /*
                .requestMatchers(
                    "/webjars/**", //
                    "/resources/**",
                    "/",
                    "/index",
                    "/favicon.ico")
                 */
                .requestMatchers(
                    mvc.pattern("/webjars/**"), //
                    mvc.pattern("/resources/**"),
                    mvc.pattern("/"),
                    mvc.pattern("/index"),
                    mvc.pattern("/favicon.ico"))
                .permitAll()
                .requestMatchers(mvc.pattern(HttpMethod.DELETE, "/todos/*"))
                .hasAuthority("ADMIN")
                .anyRequest()
                .authenticated());
    http.formLogin(withDefaults());
    http.csrf(withDefaults());
    return http.build();
  }
}
