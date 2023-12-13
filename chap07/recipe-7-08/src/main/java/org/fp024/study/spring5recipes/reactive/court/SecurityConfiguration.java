package org.fp024.study.spring5recipes.reactive.court;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

  @Bean
  SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
    return http.authorizeExchange(
            conf ->
                conf.pathMatchers(
                        "/webjars_locator/**", //
                        "/resources/**",
                        "/",
                        "/index",
                        "/welcome",
                        "/welcome/**")
                    .permitAll()
                    .pathMatchers("/reservation*")
                    .hasRole("USER")
                    .pathMatchers("/users/{user}/**")
                    .access(this::currentUserMatchesPath)
                    .anyExchange()
                    .authenticated())
        .formLogin(withDefaults())
        .csrf(withDefaults())
        .build();
  }

  private Mono<AuthorizationDecision> currentUserMatchesPath(
      Mono<Authentication> authentication, AuthorizationContext context) {
    return authentication
        .map(
            a ->
                context.getVariables().get("user").equals(a.getName())
                    || a.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
        .map(granted -> new AuthorizationDecision(granted));
  }

  @Bean
  MapReactiveUserDetailsService userDetailsService() {
    UserDetails rob =
        User.withUsername("marten") //
            .password("{noop}secret")
            .roles("USER")
            .build();
    UserDetails admin =
        User.withUsername("admin") //
            .password("{noop}admin")
            .roles("USER", "ADMIN")
            .build();
    return new MapReactiveUserDetailsService(rob, admin);
  }
}
