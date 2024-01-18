package org.fp024.study.spring5recipes.vehicle;

import org.fp024.study.spring5recipes.vehicle.dao.VehicleDao;
import org.fp024.study.spring5recipes.vehicle.domain.Vehicle;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
public class Main {
  private final VehicleDao vehicleDao;

  public Main(VehicleDao vehicleDao) {
    this.vehicleDao = vehicleDao;
  }

  void run(String[] args) {
    Vehicle vehicle = new Vehicle("TEM0001", "Red", 4, 4);
    vehicleDao.insert(vehicle);

    vehicle = vehicleDao.findByVehicleNo("TEM0001");
    System.out.println(vehicle);
  }

  public static void main(String[] args) {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

    context
        .getBean(Main.class) //
        .run(args);
  }
}
