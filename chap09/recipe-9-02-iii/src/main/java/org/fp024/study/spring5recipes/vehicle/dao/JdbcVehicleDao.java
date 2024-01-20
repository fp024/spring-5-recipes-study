package org.fp024.study.spring5recipes.vehicle.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import org.fp024.study.spring5recipes.vehicle.domain.Vehicle;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcVehicleDao implements VehicleDao {

  private static final String INSERT_SQL =
      """
      INSERT INTO vehicle (color, wheel, seat, vehicle_no)
      VALUES (?, ?, ?, ?)
      """;
  private static final String UPDATE_SQL =
      """
      UPDATE vehicle
         SET color = ?
           , wheel = ?
           , seat = ?
       WHERE vehicle_no = ?
      """;
  private static final String SELECT_ALL_SQL =
      """
       SELECT *
        FROM vehicle
      """;
  private static final String SELECT_ONE_SQL =
      """
      SELECT *
        FROM vehicle
       WHERE vehicle_no = ?
      """;
  private static final String DELETE_SQL =
      """
      DELETE
        FROM vehicle
       WHERE vehicle_no = ?
      """;
  private final JdbcTemplate jdbcTemplate;

  public JdbcVehicleDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void insert(Vehicle vehicle) {
    jdbcTemplate.update(
        INSERT_SQL,
        vehicle.getColor(),
        vehicle.getWheel(),
        vehicle.getSeat(),
        vehicle.getVehicleNo());
  }

  @Override
  public void insert(Collection<Vehicle> vehicles) {
    jdbcTemplate.batchUpdate(INSERT_SQL, vehicles, vehicles.size(), this::prepareStatement);
  }

  @Override
  public Vehicle findByVehicleNo(String vehicleNo) {
    try {
      // ✨ 레시피 주제
      return jdbcTemplate.queryForObject(
          SELECT_ONE_SQL, BeanPropertyRowMapper.newInstance(Vehicle.class), vehicleNo);
    } catch (EmptyResultDataAccessException e) {
      // queryForObject는 결과가 반드시 1개 있을 것을 간주하기 때문에, 예외 처리를 해둔다.
      return null;
    }
  }

  @Override
  public List<Vehicle> findAll() {
    // ✨ 레시피 주제
    return jdbcTemplate.query(SELECT_ALL_SQL, BeanPropertyRowMapper.newInstance(Vehicle.class));
  }

  private void prepareStatement(PreparedStatement ps, Vehicle vehicle) throws SQLException {
    ps.setString(1, vehicle.getColor());
    ps.setInt(2, vehicle.getWheel());
    ps.setInt(3, vehicle.getSeat());
    ps.setString(4, vehicle.getVehicleNo());
  }

  @Override
  public void update(Vehicle vehicle) {
    jdbcTemplate.update(
        UPDATE_SQL,
        vehicle.getColor(),
        vehicle.getWheel(),
        vehicle.getSeat(),
        vehicle.getVehicleNo());
  }

  @Override
  public void delete(Vehicle vehicle) {
    jdbcTemplate.update(DELETE_SQL, vehicle.getVehicleNo());
  }
}
