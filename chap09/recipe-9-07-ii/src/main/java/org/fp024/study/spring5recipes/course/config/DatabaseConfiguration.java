package org.fp024.study.spring5recipes.course.config;

import java.util.Properties;
import org.fp024.study.spring5recipes.course.domain.Course;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.tool.schema.Action;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

abstract class DatabaseConfiguration {

  protected abstract Environment getEnv();

  // ✨ 레시피 주제
  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
    sessionFactoryBean.setHibernateProperties(hibernateProperties());
    sessionFactoryBean.setAnnotatedClasses(Course.class);
    return sessionFactoryBean;
  }

  private Properties hibernateProperties() {
    Properties props = new Properties();

    props.setProperty(AvailableSettings.URL, getEnv().getProperty("jdbc.url"));
    props.setProperty(AvailableSettings.USER, getEnv().getProperty("jdbc.username"));
    props.setProperty(AvailableSettings.PASS, getEnv().getProperty("jdbc.password"));
    props.setProperty(AvailableSettings.DIALECT, getEnv().getProperty("orm.hibernate.dialect"));

    props.setProperty(AvailableSettings.SHOW_SQL, String.valueOf(false));
    props.setProperty(AvailableSettings.FORMAT_SQL, String.valueOf(true));
    props.setProperty(AvailableSettings.HBM2DDL_AUTO, Action.CREATE.getExternalHbm2ddlName());

    // ✨ HikariCP 설정
    props.setProperty(
        "hibernate.connection.provider_class",
        "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");
    props.setProperty("hibernate.hikari.minimumIdle", String.valueOf(5));
    props.setProperty("hibernate.hikari.maximumPoolSize", String.valueOf(10));
    props.setProperty("hibernate.hikari.idleTimeout", String.valueOf(30000));

    return props;
  }
}
