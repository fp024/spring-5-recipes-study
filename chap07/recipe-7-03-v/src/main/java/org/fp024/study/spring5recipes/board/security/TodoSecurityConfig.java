package org.fp024.study.spring5recipes.board.security;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.ldap.test.unboundid.TestContextSourceFactoryBean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class TodoSecurityConfig {

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  /** <a href="https://docs.spring.io/spring-ldap/reference/testing.html">LDAP 테스트 가이드 문서</a> */
  @Bean
  TestContextSourceFactoryBean contextSource() {
    TestContextSourceFactoryBean factory = new TestContextSourceFactoryBean();
    factory.setDefaultPartitionName("springrecipes");
    // factory.setBaseOnTarget(true);
    factory.setLdifFile(new ClassPathResource("server.ldif"));
    factory.setDefaultPartitionSuffix("dc=springrecipes,dc=com");
    // ✨ EmbeddedLdapServer 에 미리 정의된 관리자 계정/비밀번호 설정
    factory.setPrincipal("uid=admin,ou=system");
    factory.setPassword("secret");
    factory.setPort(33389);
    return factory;
  }

  @Autowired
  void ldapSetting(AuthenticationManagerBuilder auth) throws Exception {
    auth.ldapAuthentication()
        .contextSource()
        .url("ldap://localhost:33389/dc=springrecipes,dc=com")
        // ✨ EmbeddedLdapServer 에 미리 정의된 관리자 계정/비밀번호 설정
        .managerDn("uid=admin,ou=system")
        .managerPassword("secret")
        .and()
        .userSearchFilter("uid={0}")
        .userSearchBase("ou=people")
        .groupSearchFilter("member={0}")
        .groupSearchBase("ou=groups")
        .rolePrefix("")
        .passwordCompare()
        .passwordEncoder(passwordEncoder())
        .passwordAttribute("userPassword");
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
        // .rememberMe(withDefaults())
        // ✨ TODO: LDAP 전역 설정을 했을 때, 어떻게 UserDetails를 넣어줄 수 있을지 지금은 모르겠다.
        .logout(configurer -> configurer.logoutSuccessUrl("/logout-success"));
    return http.build();
  }
}
