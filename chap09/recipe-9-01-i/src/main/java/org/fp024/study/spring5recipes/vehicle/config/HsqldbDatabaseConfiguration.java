package org.fp024.study.spring5recipes.vehicle.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Profile("hsqldb")
@Configuration
@PropertySource("classpath:database-hsqldb.properties")
public class HsqldbDatabaseConfiguration extends DatabaseConfiguration {

  @Getter(AccessLevel.PROTECTED)
  private final Environment env;

  public HsqldbDatabaseConfiguration(Environment env) {
    this.env = env;
  }
}
