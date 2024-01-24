package org.fp024.study.spring5recipes.vehicle;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.vehicle.dao.VehicleDao;
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
    LOGGER.info("command args: {}", Arrays.toString(args));
    var count = vehicleDao.countAll();
    System.out.printf("Vehicle Count: %d%n", count);

    var vehicleNo = "TEM1001";
    var color = vehicleDao.getColor(vehicleNo);
    System.out.printf("Color for [%s]: %s%n", vehicleDao, color);
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
