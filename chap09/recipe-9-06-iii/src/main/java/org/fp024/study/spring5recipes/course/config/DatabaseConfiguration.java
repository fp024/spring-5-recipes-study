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

    jpaProperties.setProperty(AvailableSettings.JPA_JDBC_URL, getEnv().getProperty("jdbc.url"));
    jpaProperties.setProperty(
        AvailableSettings.JPA_JDBC_USER, getEnv().getProperty("jdbc.username"));
    jpaProperties.setProperty(
        AvailableSettings.JPA_JDBC_PASSWORD, getEnv().getProperty("jdbc.password"));
    jpaProperties.setProperty(
        AvailableSettings.DIALECT, getEnv().getProperty("orm.hibernate.dialect"));

    jpaProperties.setProperty(AvailableSettings.SHOW_SQL, String.valueOf(false));
    jpaProperties.setProperty(AvailableSettings.FORMAT_SQL, String.valueOf(true));
    jpaProperties.setProperty(
        AvailableSettings.HBM2DDL_AUTO, Action.CREATE.getExternalHbm2ddlName());

    // ✨ HikariCP 설정
    jpaProperties.setProperty(
        "hibernate.connection.provider_class",
        "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");
    jpaProperties.setProperty("hibernate.hikari.minimumIdle", String.valueOf(5));
    jpaProperties.setProperty("hibernate.hikari.maximumPoolSize", String.valueOf(10));
    jpaProperties.setProperty("hibernate.hikari.idleTimeout", String.valueOf(30000));

    return jpaProperties;
  }
}
