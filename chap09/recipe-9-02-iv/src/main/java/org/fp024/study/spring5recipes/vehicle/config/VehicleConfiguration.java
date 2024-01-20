package org.fp024.study.spring5recipes.vehicle.config;

import javax.sql.DataSource;
import org.fp024.study.spring5recipes.vehicle.dao.JdbcVehicleDao;
import org.fp024.study.spring5recipes.vehicle.dao.VehicleDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class VehicleConfiguration {

  @Bean
  VehicleDao vehicleDao(DataSource dataSource) {
    return new JdbcVehicleDao(jdbcTemplate(dataSource));
  }

  @Bean
  JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }
}
