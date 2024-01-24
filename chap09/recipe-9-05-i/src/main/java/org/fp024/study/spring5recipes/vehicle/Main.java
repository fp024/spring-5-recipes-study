package org.fp024.study.spring5recipes.vehicle;

import java.sql.SQLException;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.vehicle.dao.VehicleDao;
import org.fp024.study.spring5recipes.vehicle.domain.Vehicle;
import org.fp024.study.spring5recipes.vehicle.util.DbResetUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;

@Slf4j
@ComponentScan
@Configuration
public class Main {
  private final VehicleDao vehicleDao;

  private final DbResetUtils dbResetUtils;

  public Main(VehicleDao vehicleDao, DbResetUtils dbResetUtils) {
    this.vehicleDao = vehicleDao;
    this.dbResetUtils = dbResetUtils;
  }

  void run(String[] args) {
    // 실행 전에 DB 초기화
    dbResetUtils.resetDB();

    LOGGER.info("command args: {}", Arrays.toString(args));

    // ✨EX0001 자동차 번호를 가진 데이터가 이미 저장되어있음.
    Vehicle ex0001 = vehicleDao.findByVehicleNo("EX0001");
    LOGGER.info("Vehicle ex0001: {}", ex0001);

    // ✨EX0001 자동차 번호를 다시 저장하려함.
    Vehicle vehicle = new Vehicle("EX0001", "Green", 4, 4);
    vehicleDao.insert(vehicle);
  }

  public static void main(String[] args) {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(Main.class)) {
      context
          .getBean(Main.class) //
          .run(args);
    } catch (DataAccessException e) {
      SQLException sqle = (SQLException) e.getCause();
      LOGGER.info("### Error code: {}", sqle.getErrorCode());
      LOGGER.info("### SQL state: {}", sqle.getSQLState());
      throw e;
    }
  }
}
