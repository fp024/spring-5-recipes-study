package org.fp024.study.spring5recipes.board.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.acls.AclEntryVoter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionVoter;

@Configuration
@EnableWebSecurity
// @EnableMethodSecurity // ✨
@EnableGlobalMethodSecurity(prePostEnabled = true) // ✨
public class TodoSecurityConfig {

  private final DataSource dataSource;

  public TodoSecurityConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  UserDetailsManager userDetailsService() {
    return new JdbcUserDetailsManager(dataSource);
  }

  // 😈 5.8.x에서는 Deprecated, 5.7.x에서는 아님.
  @Bean
  AffirmativeBased accessDecisionManager(AclEntryVoter aclEntryVoter) {
    List<AccessDecisionVoter<?>> decisionVoters =
        Arrays.asList(new WebExpressionVoter(), aclEntryVoter);
    return new AffirmativeBased(decisionVoters);
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
