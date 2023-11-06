package org.fp024.study.spring5recipes.springbatch.outer.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@RequiredArgsConstructor
@EnableJpaRepositories(
    basePackages = "org.fp024.study.spring5recipes.springbatch.outer.repository",
    transactionManagerRef = "serviceTransactionManager")
@PropertySource("classpath:database-service.properties")
@Configuration
public class ServiceConfiguration {
  private final Environment env;

  @Bean(name = "serviceDataSource", destroyMethod = "close")
  HikariDataSource serviceDataSource() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(env.getProperty("service.jdbc.driver"));
    hikariConfig.setJdbcUrl(env.getProperty("service.jdbc.url"));
    hikariConfig.setUsername(env.getProperty("service.jdbc.username"));
    hikariConfig.setPassword(env.getProperty("service.jdbc.password"));
    return new HikariDataSource(hikariConfig);
  }

  @Bean
  DataSourceInitializer serviceDataSourceInitializer() {
    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(serviceDataSource());
    // 데이터 베이스 채우기
    initializer.setDatabasePopulator(databasePopulator());
    return initializer;
  }

  private DatabasePopulator databasePopulator() {
    ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
    databasePopulator.setContinueOnError(false);
    databasePopulator.setIgnoreFailedDrops(true);
    databasePopulator.addScript(new ClassPathResource("sql/reset_user_registration.sql"));
    return databasePopulator;
  }

  @Bean
  ModelMapper getMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper
        .getConfiguration() //
        .setFieldMatchingEnabled(true)
        .setFieldAccessLevel(AccessLevel.PRIVATE)
        .setMatchingStrategy(MatchingStrategies.STRICT);

    return modelMapper;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      @Qualifier("serviceDataSource") DataSource dataSource) throws IOException {

    LocalContainerEntityManagerFactoryBean emfBean = new LocalContainerEntityManagerFactoryBean();

    emfBean.setDataSource(dataSource);
    emfBean.setPersistenceUnitName("spring_batch_study");
    emfBean.setPackagesToScan("org.fp024.study.spring5recipes.springbatch.outer.domain");

    HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();

    hibernateJpaVendorAdapter.setDatabase(Database.HSQL);
    hibernateJpaVendorAdapter.setShowSql(true);
    emfBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);

    Properties properties = new Properties();

    properties.load(
        new EncodedResource(new ClassPathResource("jpa.properties"), StandardCharsets.UTF_8)
            .getInputStream());

    emfBean.setJpaProperties(properties);

    return emfBean;
  }

  @Bean
  public JpaTransactionManager serviceTransactionManager(
      EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);
    return transactionManager;
  }
}
