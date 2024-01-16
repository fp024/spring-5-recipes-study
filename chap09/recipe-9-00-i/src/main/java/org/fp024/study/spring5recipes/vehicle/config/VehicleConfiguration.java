package org.fp024.study.spring5recipes.vehicle.config;

import java.nio.charset.StandardCharsets;
import java.sql.Driver;
import javax.sql.DataSource;
import org.fp024.study.spring5recipes.vehicle.dao.PlainJdbcVehicleDao;
import org.fp024.study.spring5recipes.vehicle.dao.VehicleDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@ComponentScan("org.fp024.study.spring5recipes.vehicle")
@PropertySource("classpath:database.properties")
public class VehicleConfiguration {

  private final Environment env;

  public VehicleConfiguration(Environment env) {
    this.env = env;
  }

  @Bean
  VehicleDao vehicleDao(DataSource dataSource) {
    return new PlainJdbcVehicleDao(dataSource);
  }

  @Bean
  public DataSource dataSource() {
    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
    dataSource.setDriverClass(getDriver(env.getProperty("jdbc.driver")));
    dataSource.setUrl(env.getProperty("jdbc.url"));
    dataSource.setUsername(env.getProperty("jdbc.username"));
    dataSource.setPassword(env.getProperty("jdbc.password"));
    return dataSource;
  }

  @SuppressWarnings("unchecked")
  private Class<? extends Driver> getDriver(String driverClassName) {
    try {

      return (Class<? extends Driver>) Class.forName(driverClassName);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Bean
  DataSourceInitializer dataSourceInitializer() {
    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource());
    initializer.setDatabasePopulator(databasePopulator());
    return initializer;
  }

  @Bean
  ResourceDatabasePopulator databasePopulator() {
    ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
    databasePopulator.setSqlScriptEncoding(StandardCharsets.UTF_8.name());
    databasePopulator.setContinueOnError(false);
    databasePopulator.addScript(new ClassPathResource("schema.sql"));

    return databasePopulator;
  }
}
