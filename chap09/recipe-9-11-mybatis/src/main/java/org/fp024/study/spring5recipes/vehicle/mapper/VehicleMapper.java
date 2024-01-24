package org.fp024.study.spring5recipes.vehicle.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.fp024.study.spring5recipes.vehicle.domain.Vehicle;

@Mapper
public interface VehicleMapper {
  void insert(Vehicle vehicle);

  /*
   원래 "JdbcTemplate"을 활용한 코드는 다음 메서드가 있었는데,
   MyBatis Mapper XML에서 같이 쓰려니 사용이 불가능해 보인다,
   그래서 DAO에서 "SqlSession"을 받아서 Java 코드로 구현했다.

   void insert(Collection<Vehicle> vehicles);
  */
  void insertVehicles(List<Vehicle> vehicles);

  void update(Vehicle vehicle);

  void delete(Vehicle vehicle);

  Vehicle findByVehicleNo(String vehicleNo);

  List<Vehicle> findAll();

  String getColor(String vehicleNo);

  Integer countAll();
}
