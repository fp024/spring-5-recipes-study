package org.fp024.study.spring5recipes.vehicle.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Profile({"mysql", "default"})
@Configuration
@PropertySource("classpath:database-mysql.properties")
public class MySqlDatabaseConfiguration extends DatabaseConfiguration {

  @Getter(AccessLevel.PROTECTED)
  private final Environment env;

  public MySqlDatabaseConfiguration(Environment env) {
    this.env = env;
  }
}
