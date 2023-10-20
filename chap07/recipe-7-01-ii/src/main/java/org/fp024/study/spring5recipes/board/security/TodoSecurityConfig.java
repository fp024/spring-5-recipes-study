package org.fp024.study.spring5recipes.board.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/*
  WebSecurityConfigurerAdapter가 지원 중단 되었고,
  org.springframework.security.web.SecurityFilterChain 빈을 사용하여
  HttpSecurity를 구성하거나 WebSecurityCustomizer 빈을 사용하여 WebSecurity를 구성하라고 함.

  WebSecurityConfigurerAdapter 상속 코드는 넣어두지 말자!
*/
@Configuration
@EnableWebSecurity
public class TodoSecurityConfig {}
