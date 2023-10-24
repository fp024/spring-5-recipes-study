package org.fp024.study.spring5recipes.board.security;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class TodoSecurityConfig {

  private final DataSource dataSource;

  private final CacheManager cacheManager;

  @Bean
  UserCache userCache() {
    Cache cache = cacheManager.getCache("userCache");
    return new SpringCacheBasedUserCache(cache);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  JdbcUserDetailsManager jdbcUserDetailsManager() {
    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
    jdbcUserDetailsManager.setUserCache(userCache());
    return jdbcUserDetailsManager;
  }

  @Bean
  AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserCache(userCache());
    provider.setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsService(jdbcUserDetailsManager());
    return provider;
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
