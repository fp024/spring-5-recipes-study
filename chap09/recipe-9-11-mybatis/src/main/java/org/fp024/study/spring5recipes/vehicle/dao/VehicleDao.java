package org.fp024.study.spring5recipes.vehicle.dao;

import java.util.List;
import org.fp024.study.spring5recipes.vehicle.domain.Vehicle;

public interface VehicleDao {
  void insert(Vehicle vehicle);

  void insert(List<Vehicle> vehicles);

  void insertVehicles(List<Vehicle> vehicles);

  void update(Vehicle vehicle);

  void delete(Vehicle vehicle);

  Vehicle findByVehicleNo(String vehicleNo);

  List<Vehicle> findAll();

  String getColor(String vehicleNo);

  Integer countAll();
}
