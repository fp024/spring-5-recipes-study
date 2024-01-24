package org.fp024.study.spring5recipes.vehicle.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.function.Consumer;
import org.fp024.study.spring5recipes.vehicle.Main;
import org.fp024.study.spring5recipes.vehicle.domain.Vehicle;
import org.fp024.study.spring5recipes.vehicle.util.DbResetUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig({Main.class})
class VehicleDaoTests {

  @Autowired private VehicleDao vehicleDao;

  @Autowired private DbResetUtils dbResetUtils;

  @BeforeEach
  void beforeEach() {
    dbResetUtils.resetDB();
  }

  @Test
  void findByVehicleNo() {
    var v1 = vehicleDao.findByVehicleNo("TEM1000");

    assertThat(v1) //
        .isNotNull()
        .hasFieldOrPropertyWithValue("vehicleNo", "TEM1000");
  }

  @Test
  void findAll() {
    List<Vehicle> result = vehicleDao.findAll();
    assertThat(result).isNotEmpty();
  }

  @Test
  void insert() {
    Vehicle v1 = new Vehicle("TEM0001", "Red", 4, 4);
    vehicleDao.insert(v1);

    assertThat(vehicleDao.findByVehicleNo(v1.getVehicleNo())) //
        .hasFieldOrPropertyWithValue("vehicleNo", v1.getVehicleNo());
  }

  @Test
  void insertVehicles() {
    multiInsertTemplate(vehicles -> vehicleDao.insertVehicles(vehicles));
  }

  @Test
  void insertVehicles_batchUpdate() {
    multiInsertTemplate(vehicles -> vehicleDao.insert(vehicles));
  }

  private void multiInsertTemplate(Consumer<List<Vehicle>> consumer) {
    List<Vehicle> vehicles =
        List.of(
            new Vehicle("TEM2001", "Yellow", 4, 6),
            new Vehicle("TEM2002", "Silver", 4, 4),
            new Vehicle("TEM2003", "White", 4, 2));

    consumer.accept(vehicles);

    assertThat(vehicleDao.findByVehicleNo("TEM2001"))
        .hasFieldOrPropertyWithValue("vehicleNo", "TEM2001");
    assertThat(vehicleDao.findByVehicleNo("TEM2002"))
        .hasFieldOrPropertyWithValue("vehicleNo", "TEM2002");
    assertThat(vehicleDao.findByVehicleNo("TEM2003"))
        .hasFieldOrPropertyWithValue("vehicleNo", "TEM2003");
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

  @Test
  void getColor() {
    assertThat(vehicleDao.getColor("TEM1001")) //
        .isEqualTo("Gray");
  }

  @Test
  void countAll() {
    assertThat(vehicleDao.countAll()) //
        .isPositive();
  }
}
