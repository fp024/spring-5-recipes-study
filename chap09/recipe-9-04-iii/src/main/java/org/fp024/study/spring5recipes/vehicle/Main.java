package org.fp024.study.spring5recipes.vehicle;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.vehicle.dao.VehicleDao;
import org.fp024.study.spring5recipes.vehicle.domain.Vehicle;
import org.fp024.study.spring5recipes.vehicle.util.DbResetUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@ComponentScan
@Configuration
public class Main {
  private final VehicleDao vehicleDao;

  private final DbResetUtils dbResetUtils;

  public Main(VehicleDao vehicleDao, DbResetUtils dbResetUtils) {
    this.vehicleDao = vehicleDao;
    this.dbResetUtils = dbResetUtils;
  }

  void run(String[] args) {
    // 실행 전에 DB 초기화
    dbResetUtils.resetDB();

    LOGGER.info("commonad args:", Arrays.toString(args));

    Vehicle vehicle = new Vehicle("TEM0043", "Red", 4, 4);
    vehicleDao.insert(vehicle);

    vehicle = vehicleDao.findByVehicleNo("TEM0043");
    System.out.printf("Vehicle No: %s%n", vehicle.getVehicleNo());
    System.out.printf("Color: %s%n", vehicle.getColor());
    System.out.printf("Wheel: %d%n", vehicle.getWheel());
    System.out.printf("Seat: %d%n", vehicle.getSeat());
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
