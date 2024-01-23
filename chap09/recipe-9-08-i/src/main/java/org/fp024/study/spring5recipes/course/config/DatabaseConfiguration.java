package org.fp024.study.spring5recipes.course.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import org.fp024.study.spring5recipes.course.domain.Course;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.tool.schema.Action;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
abstract class DatabaseConfiguration {

  protected abstract Environment getEnv();

  // ✨ 레시피 주제
  @Bean(destroyMethod = "close")
  HikariDataSource dataSource() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(getEnv().getProperty("jdbc.driver"));
    hikariConfig.setJdbcUrl(getEnv().getProperty("jdbc.url"));
    hikariConfig.setUsername(getEnv().getProperty("jdbc.username"));
    hikariConfig.setPassword(getEnv().getProperty("jdbc.password"));
    hikariConfig.setMinimumIdle(2);
    hikariConfig.setMaximumPoolSize(5);
    return new HikariDataSource(hikariConfig);
  }

  // ✨ 레시피 주제
  @Bean
  LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
    sessionFactoryBean.setDataSource(dataSource());
    sessionFactoryBean.setHibernateProperties(hibernateProperties());
    sessionFactoryBean.setAnnotatedClasses(Course.class);
    return sessionFactoryBean;
  }

  private Properties hibernateProperties() {
    Properties props = new Properties();
    props.setProperty(AvailableSettings.DIALECT, getEnv().getProperty("orm.hibernate.dialect"));

    props.setProperty(AvailableSettings.SHOW_SQL, String.valueOf(false));
    props.setProperty(AvailableSettings.FORMAT_SQL, String.valueOf(true));
    props.setProperty(AvailableSettings.HBM2DDL_AUTO, Action.CREATE.getExternalHbm2ddlName());

    return props;
  }

  @Bean
  HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
    return new HibernateTransactionManager(sessionFactory);
  }
}
