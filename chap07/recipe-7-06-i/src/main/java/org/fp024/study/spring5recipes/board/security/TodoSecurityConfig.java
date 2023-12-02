package org.fp024.study.spring5recipes.board.security;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class TodoSecurityConfig {

  private final DataSource dataSource;

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  UserDetailsManager userDetailsService() {
    return new JdbcUserDetailsManager(dataSource);
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            (authz) ->
                authz
                    .requestMatchers(
                        "/webjars/**", //
                        "/resources/**", //
                        "/login", //
                        "/logout-success", //
                        "/",
                        "/index",
                        "/favicon.ico")
                    .permitAll()
                    .requestMatchers(HttpMethod.DELETE, "/todos/*")
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
