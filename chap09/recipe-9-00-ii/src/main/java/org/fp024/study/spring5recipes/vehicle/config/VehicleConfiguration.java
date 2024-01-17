package org.fp024.study.spring5recipes.vehicle.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.nio.charset.StandardCharsets;
import javax.sql.DataSource;
import org.fp024.study.spring5recipes.vehicle.dao.PlainJdbcVehicleDao;
import org.fp024.study.spring5recipes.vehicle.dao.VehicleDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@ComponentScan("org.fp024.study.spring5recipes.vehicle")
@PropertySource("classpath:database-mysql.properties")
public class VehicleConfiguration {

  private final Environment env;

  public VehicleConfiguration(Environment env) {
    this.env = env;
  }

  @Bean
  VehicleDao vehicleDao(DataSource dataSource) {
    return new PlainJdbcVehicleDao(dataSource);
  }

  @Bean(destroyMethod = "close")
  HikariDataSource dataSource() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(env.getProperty("jdbc.driver"));
    hikariConfig.setJdbcUrl(env.getProperty("jdbc.url"));
    hikariConfig.setUsername(env.getProperty("jdbc.username"));
    hikariConfig.setPassword(env.getProperty("jdbc.password"));
    hikariConfig.setMinimumIdle(2);
    hikariConfig.setMaximumPoolSize(5);
    return new HikariDataSource(hikariConfig);
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
    databasePopulator.addScript(new ClassPathResource("sql/mysql/schema.sql"));

    return databasePopulator;
  }
}
