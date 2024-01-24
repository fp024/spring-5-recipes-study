package org.fp024.study.spring5recipes.vehicle.dao;

import java.util.List;
import org.fp024.study.spring5recipes.vehicle.domain.Vehicle;
import org.fp024.study.spring5recipes.vehicle.mapper.VehicleMapper;

public interface VehicleDao extends VehicleMapper {
  void insert(List<Vehicle> vehicles);
}
