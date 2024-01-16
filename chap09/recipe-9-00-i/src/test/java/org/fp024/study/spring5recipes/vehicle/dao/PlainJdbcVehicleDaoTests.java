package org.fp024.study.spring5recipes.vehicle.dao;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.vehicle.config.VehicleConfiguration;
import org.fp024.study.spring5recipes.vehicle.domain.Vehicle;
import org.fp024.study.spring5recipes.vehicle.test.DBTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Slf4j
@SpringJUnitConfig(classes = VehicleConfiguration.class)
class PlainJdbcVehicleDaoTests {

  @Autowired private VehicleDao vehicleDao;

  @Autowired private DBTestUtils dbTestUtils;

  @AfterEach
  void AfterEach() {
    dbTestUtils.resetDB();
  }

  @Test
  void delete() {
    LOGGER.info("### delete");
    Vehicle v1 = new Vehicle();
    v1.setVehicleNo("TEM0001");
    vehicleDao.delete(v1);
  }

  @Test
  void insert() {
    LOGGER.info("### insert");
    Vehicle v1 = new Vehicle("TEM0001", "Red", 4, 4);
    vehicleDao.insert(v1);
  }

  @Test
  void findAll() {
    LOGGER.info("### findAll");
    var result = vehicleDao.findAll();
    assertThat(result) //
        .isNotEmpty();
  }
}
