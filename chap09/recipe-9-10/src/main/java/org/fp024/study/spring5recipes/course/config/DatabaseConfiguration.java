package org.fp024.study.spring5recipes.course.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.tool.schema.Action;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableJpaRepositories("org.fp024.study.spring5recipes.course.dao")
abstract class DatabaseConfiguration {

  protected abstract Environment getEnv();

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
  LocalContainerEntityManagerFactoryBean entityManagerFactory() {

    LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
    emf.setPersistenceUnitName("course");
    emf.setDataSource(dataSource());
    emf.setJpaVendorAdapter(jpaVendorAdapter());
    emf.setJpaProperties(hibernateProperties());
    emf.setPackagesToScan("org.fp024.study.spring5recipes.course.domain");
    return emf;
  }

  private JpaVendorAdapter jpaVendorAdapter() {
    HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
    jpaVendorAdapter.setShowSql(false);
    return jpaVendorAdapter;
  }

  private Properties hibernateProperties() {
    Properties props = new Properties();
    props.setProperty(AvailableSettings.HBM2DDL_AUTO, Action.CREATE.getExternalHbm2ddlName());
    props.setProperty(AvailableSettings.FORMAT_SQL, String.valueOf(true));
    return props;
  }

  @Bean
  JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
