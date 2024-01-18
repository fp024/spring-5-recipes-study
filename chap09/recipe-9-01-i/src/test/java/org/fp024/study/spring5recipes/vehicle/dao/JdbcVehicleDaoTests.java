package org.fp024.study.spring5recipes.vehicle.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.sun.tools.javac.Main;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.vehicle.domain.Vehicle;
import org.fp024.study.spring5recipes.vehicle.test.DBTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Slf4j
@ActiveProfiles({"mysql", "hsqldb"})
@SpringJUnitConfig({Main.class})
class JdbcVehicleDaoTests {

  @Autowired private VehicleDao vehicleDao;

  @Autowired private DBTestUtils dbTestUtils;

  @AfterEach
  void AfterEach() {
    dbTestUtils.resetDB();
  }

  @Test
  void insert() {
    Vehicle v1 = new Vehicle("TEM0001", "Red", 4, 4);
    vehicleDao.insert(v1);

    assertThat(vehicleDao.findByVehicleNo(v1.getVehicleNo())) //
        .hasFieldOrPropertyWithValue("vehicleNo", v1.getVehicleNo());
  }
}
