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

@Configuration
@EnableWebSecurity
public class TodoSecurityConfig {

  @Bean
  UserDetailsService userDetailsService() {
    val admin =
        User.withUsername("admin") //
            .password("{noop}admin")
            .authorities("USER", "ADMIN")
            .build();
    val user00 =
        User.withUsername("user00") //
            .password("{noop}user00")
            .authorities("USER")
            .build();
    // user01 (비활성 계정)
    val user01 =
        User.withUsername("user01") //
            .password("{noop}user01")
            .authorities("USER")
            .disabled(true)
            .build();

    return new InMemoryUserDetailsManager(admin, user00, user01);
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            (authz) ->
                authz
                    .requestMatchers(
                        antMatcher("/webjars/**"), //
                        antMatcher("/resources/**"), //
                        antMatcher("/login"), //
                        antMatcher("/logout-success"), //
                        antMatcher("/"),
                        antMatcher("/index"),
                        antMatcher("/favicon.ico"))
                    .permitAll()
                    .requestMatchers(antMatcher(HttpMethod.DELETE, "/todos/*"))
                    .hasAuthority("ADMIN")
                    .anyRequest()
                    .authenticated())
        .formLogin(
            configurer ->
                configurer
                    .loginPage("/login") //
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/todos")
                    .failureUrl("/login?error=true"))
        .rememberMe(withDefaults())
        .logout(configurer -> configurer.logoutSuccessUrl("/logout-success"));
    return http.build();
  }
}
