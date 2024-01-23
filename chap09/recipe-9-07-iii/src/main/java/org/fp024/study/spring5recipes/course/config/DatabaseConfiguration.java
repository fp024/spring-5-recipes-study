package org.fp024.study.spring5recipes.course.config;

import java.util.Properties;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.hikaricp.internal.HikariCPConnectionProvider;
import org.hibernate.tool.schema.Action;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

abstract class DatabaseConfiguration {

  protected abstract Environment getEnv();

  // ✨ 레시피 주제
  @Bean
  public LocalEntityManagerFactoryBean entityManagerFactory() {

    LocalEntityManagerFactoryBean emf = new LocalEntityManagerFactoryBean();
    emf.setPersistenceUnitName("course");
    emf.setJpaProperties(hibernateProperties());

    return emf;
  }

  private Properties hibernateProperties() {
    Properties props = new Properties();

    props.setProperty(AvailableSettings.JPA_JDBC_URL, getEnv().getProperty("jdbc.url"));
    props.setProperty(AvailableSettings.JPA_JDBC_USER, getEnv().getProperty("jdbc.username"));
    props.setProperty(AvailableSettings.JPA_JDBC_PASSWORD, getEnv().getProperty("jdbc.password"));
    props.setProperty(AvailableSettings.DIALECT, getEnv().getProperty("orm.hibernate.dialect"));

    props.setProperty(AvailableSettings.SHOW_SQL, String.valueOf(false));
    props.setProperty(AvailableSettings.FORMAT_SQL, String.valueOf(true));
    props.setProperty(AvailableSettings.HBM2DDL_AUTO, Action.CREATE.getExternalHbm2ddlName());

    // ✨ HikariCP 설정
    props.setProperty(
        "hibernate.connection.provider_class", HikariCPConnectionProvider.class.getName());
    props.setProperty("hibernate.hikari.minimumIdle", String.valueOf(5));
    props.setProperty("hibernate.hikari.maximumPoolSize", String.valueOf(10));
    props.setProperty("hibernate.hikari.idleTimeout", String.valueOf(30000));

    return props;
  }
}
