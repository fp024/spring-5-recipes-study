package org.fp024.study.spring5recipes.board.security;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class TodoSecurityConfig extends WebSecurityConfigurerAdapter {

  private final DataSource dataSource;

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  JdbcUserDetailsManager jdbcUserDetailsManager() {
    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
    return jdbcUserDetailsManager;
  }

  @Bean
  AccessChecker accessChecker() {
    return new AccessChecker();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers(HttpMethod.DELETE, "/todos/*")
        .access("hasAuthority('ADMIN') and @accessChecker.hasLocalAccess(authentication)")
        //
        .and()
        .formLogin()
        .loginPage("/login") //
        .loginProcessingUrl("/login")
        .defaultSuccessUrl("/todos")
        .failureUrl("/login?error=true")
        .and()
        .rememberMe()
        .userDetailsService(jdbcUserDetailsManager())
        .and()
        .logout()
        .logoutSuccessUrl("/logout-success");
  }
}
