package org.fp024.study.spring5recipes.board.security;

import java.util.List;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
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
  public AffirmativeBased accessDecisionManager() {
    List<AccessDecisionVoter<?>> decisionVoters =
        List.of(new AuthenticatedVoter(), new IpAddressVoter());
    AffirmativeBased decisionManager = new AffirmativeBased(decisionVoters);
    return decisionManager;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .accessDecisionManager(accessDecisionManager())
        .antMatchers(HttpMethod.DELETE, "/todos/*")
        .access("IP_LOCAL_HOST")
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
