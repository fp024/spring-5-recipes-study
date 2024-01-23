package org.fp024.study.spring5recipes.course.config;

import org.fp024.study.spring5recipes.course.domain.Course;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.schema.Action;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

abstract class DatabaseConfiguration {

  protected abstract Environment getEnv();

  @Bean
  SessionFactory sessionFactory() {
    Configuration configuration =
        new Configuration()
            .setProperty(AvailableSettings.URL, getEnv().getProperty("jdbc.url"))
            .setProperty(AvailableSettings.USER, getEnv().getProperty("jdbc.username"))
            .setProperty(AvailableSettings.PASS, getEnv().getProperty("jdbc.password"))
            .setProperty(AvailableSettings.DIALECT, getEnv().getProperty("orm.hibernate.dialect"))
            .setProperty(
                AvailableSettings.SHOW_SQL, String.valueOf(false)) // log4jdbc 출력과 겹쳐서 노출할 필요는 없음
            .setProperty(AvailableSettings.FORMAT_SQL, String.valueOf(true))
            .setProperty(AvailableSettings.HBM2DDL_AUTO, Action.CREATE.getExternalHbm2ddlName())
            .addAnnotatedClass(Course.class);
    return configuration.buildSessionFactory();
  }
}
