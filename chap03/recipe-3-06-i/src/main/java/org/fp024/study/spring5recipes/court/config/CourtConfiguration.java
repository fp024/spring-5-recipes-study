package org.fp024.study.spring5recipes.court.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("org.fp024.study.spring5recipes.court")
public class CourtConfiguration {}
