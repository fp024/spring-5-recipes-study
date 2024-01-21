package org.fp024.study.spring5recipes.vehicle;

import org.fp024.study.spring5recipes.vehicle.config.VehicleConfiguration;
import org.fp024.study.spring5recipes.vehicle.dao.VehicleDao;
import org.fp024.study.spring5recipes.vehicle.domain.Vehicle;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

  public static void main(String[] args) {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(VehicleConfiguration.class)) {
      VehicleDao vehicleDao = context.getBean(VehicleDao.class);
      Vehicle vehicle = new Vehicle("TEM0001", "Red", 4, 4);
      vehicleDao.insert(vehicle);

      vehicle = vehicleDao.findByVehicleNo("TEM0001");
      System.out.println(vehicle);
    }
  }
}
