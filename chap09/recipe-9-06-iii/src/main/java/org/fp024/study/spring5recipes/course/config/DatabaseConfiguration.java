package org.fp024.study.spring5recipes.course.config;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.tool.schema.Action;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

abstract class DatabaseConfiguration {

  protected abstract Environment getEnv();

  // ✨ 레시피 주제
  @Bean
  EntityManagerFactory entityManagerFactory() {
    return Persistence.createEntityManagerFactory("course", jpaProperties());
  }

  @Bean
  Properties jpaProperties() {
    Properties jpaProperties = new Properties();

    jpaProperties.setProperty("javax.persistence.jdbc.url", getEnv().getProperty("jdbc.url"));
    jpaProperties.setProperty("javax.persistence.jdbc.user", getEnv().getProperty("jdbc.username"));
    jpaProperties.setProperty(
        "javax.persistence.jdbc.password", getEnv().getProperty("jdbc.password"));
    jpaProperties.setProperty(
        AvailableSettings.DIALECT, getEnv().getProperty("orm.hibernate.dialect"));

    jpaProperties.setProperty(AvailableSettings.SHOW_SQL, String.valueOf(false));
    jpaProperties.setProperty(
        AvailableSettings.HBM2DDL_AUTO, Action.CREATE.getExternalHbm2ddlName());

    return jpaProperties;
  }
}
