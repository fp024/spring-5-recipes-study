package org.fp024.study.spring5recipes.vehicle.dao;

import java.util.List;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.fp024.study.spring5recipes.vehicle.domain.Vehicle;
import org.fp024.study.spring5recipes.vehicle.mapper.VehicleMapper;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDaoImpl implements VehicleDao {

  private final VehicleMapper vehicleMapper;
  private final SqlSessionFactory sqlSessionFactory;

  public VehicleDaoImpl(VehicleMapper vehicleMapper, SqlSessionFactory sqlSessionFactory) {
    this.vehicleMapper = vehicleMapper;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void insert(Vehicle vehicle) {
    vehicleMapper.insert(vehicle);
  }

  @Override
  public void insert(List<Vehicle> vehicles) {
    try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
      vehicles.forEach(
          vehicle -> session.update(VehicleMapper.class.getName() + ".insert", vehicle));
      session.commit();
    }
  }

  @Override
  public void insertVehicles(List<Vehicle> vehicles) {
    vehicleMapper.insertVehicles(vehicles);
  }

  @Override
  public void update(Vehicle vehicle) {
    vehicleMapper.update(vehicle);
  }

  @Override
  public void delete(Vehicle vehicle) {
    vehicleMapper.delete(vehicle);
  }

  @Override
  public Vehicle findByVehicleNo(String vehicleNo) {
    return vehicleMapper.findByVehicleNo(vehicleNo);
  }

  @Override
  public List<Vehicle> findAll() {
    return vehicleMapper.findAll();
  }

  @Override
  public String getColor(String vehicleNo) {
    return vehicleMapper.getColor(vehicleNo);
  }

  @Override
  public Integer countAll() {
    return vehicleMapper.countAll();
  }
}
