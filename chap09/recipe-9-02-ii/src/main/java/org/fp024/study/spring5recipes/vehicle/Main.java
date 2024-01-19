package org.fp024.study.spring5recipes.vehicle;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.vehicle.dao.VehicleDao;
import org.fp024.study.spring5recipes.vehicle.domain.Vehicle;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@ComponentScan
@Configuration
public class Main {
  private final VehicleDao vehicleDao;

  public Main(VehicleDao vehicleDao) {
    this.vehicleDao = vehicleDao;
  }

  void run(String[] args) {
    LOGGER.info("commonad args:", Arrays.toString(args));

    List<Vehicle> vehicles =
        List.of(
            new Vehicle("TEM0022", "Blue", 4, 4),
            new Vehicle("TEM0023", "Black", 4, 6),
            new Vehicle("TEM0024", "Green", 4, 5));

    vehicleDao.insert(vehicles);

    vehicleDao.findAll().forEach(System.out::println);
  }

  public static void main(String[] args) {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(Main.class)) {
      context
          .getBean(Main.class) //
          .run(args);
    }
  }
}
