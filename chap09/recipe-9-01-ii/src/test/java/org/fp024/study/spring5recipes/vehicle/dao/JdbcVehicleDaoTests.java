package org.fp024.study.spring5recipes.vehicle.dao;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.vehicle.Main;
import org.fp024.study.spring5recipes.vehicle.domain.Vehicle;
import org.fp024.study.spring5recipes.vehicle.test.DBTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Slf4j
@SpringJUnitConfig({Main.class})
class JdbcVehicleDaoTests {

  @Autowired private VehicleDao vehicleDao;

  @Autowired private DBTestUtils dbTestUtils;

  @BeforeEach
  void beforeEach() {
    dbTestUtils.resetDB();
  }

  @Test
  void insert() {
    Vehicle v1 = new Vehicle("TEM0001", "Red", 4, 4);
    vehicleDao.insert(v1);

    assertThat(vehicleDao.findByVehicleNo(v1.getVehicleNo())) //
        .hasFieldOrPropertyWithValue("vehicleNo", v1.getVehicleNo());
  }

  @Test
  void update() {
    Vehicle v1001 = vehicleDao.findByVehicleNo("TEM1001");
    v1001.setColor("Black");

    vehicleDao.update(v1001);

    assertThat(vehicleDao.findByVehicleNo(v1001.getVehicleNo())) //
        .hasFieldOrPropertyWithValue("color", "Black");
  }

  @Test
  void delete() {
    Vehicle v1001 = new Vehicle();
    v1001.setVehicleNo("TEM1001");
    assertThat(vehicleDao.findByVehicleNo(v1001.getVehicleNo())) //
        .hasFieldOrPropertyWithValue("vehicleNo", v1001.getVehicleNo());

    vehicleDao.delete(v1001);

    assertThat(vehicleDao.findByVehicleNo(v1001.getVehicleNo())) //
        .isNull();
  }
}
