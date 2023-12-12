package org.fp024.study.spring5recipes.board.security;

import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.acls.AclEntryVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // ✨
public class TodoSecurityConfig extends WebSecurityConfigurerAdapter {
  // 💡 5.7.x 보다 훨씬 이전 버전으로 낮춰서 테스트 해야할 때도 있어서, WebSecurityConfigurerAdapter 상속은 그냥 냅두자.

  private final DataSource dataSource;

  public TodoSecurityConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication().passwordEncoder(passwordEncoder()).dataSource(dataSource);
  }

  // 😈 5.8.x에서는 Deprecated, 5.7.x 이하에서는 아님.
  @Bean
  public AffirmativeBased accessDecisionManager(AclEntryVoter aclEntryVoter) {
    List<AccessDecisionVoter<?>> decisionVoters =
        Arrays.asList(new WebExpressionVoter(), aclEntryVoter);
    return new AffirmativeBased(decisionVoters);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests()
        .mvcMatchers(
            "/webjars/**", //
            "/resources/**", //
            "/login", //
            "/logout-success", //
            "/",
            "/index",
            "/favicon.ico")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .loginPage("/login") //
        .loginProcessingUrl("/login")
        .defaultSuccessUrl("/todos")
        .failureUrl("/login?error=true")
        .and()
        .rememberMe()
        .and()
        .logout()
        .logoutSuccessUrl("/logout-success");
  }
}
