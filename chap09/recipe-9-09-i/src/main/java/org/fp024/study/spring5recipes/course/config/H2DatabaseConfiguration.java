package org.fp024.study.spring5recipes.course.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Profile({"h2 & !default"})
@Configuration
@PropertySource("classpath:database-h2.properties")
public class H2DatabaseConfiguration extends DatabaseConfiguration {

  @Getter(AccessLevel.PROTECTED)
  private final Environment env;

  public H2DatabaseConfiguration(Environment env) {
    this.env = env;
  }
}
