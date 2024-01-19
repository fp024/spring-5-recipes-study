package org.fp024.study.spring5recipes.vehicle.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import org.fp024.study.spring5recipes.vehicle.domain.Vehicle;
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

    final var vehicle = new Vehicle();
    // ✨: 현재 레시피의 주제
    jdbcTemplate.query(
        SELECT_ONE_SQL,
        rs -> {
          vehicle.setVehicleNo(rs.getString("vehicle_no"));
          vehicle.setColor(rs.getString("color"));
          vehicle.setWheel(rs.getInt("wheel"));
          vehicle.setSeat(rs.getInt("seat"));
        },
        vehicleNo);

    // 💡조회 결과가 없을 때, null이 반환되도록 함.
    if (vehicle.getVehicleNo() == null) {
      return null;
    }

    return vehicle;
  }

  @Override
  public List<Vehicle> findAll() {
    return jdbcTemplate.query(SELECT_ALL_SQL, (rs, rowNum) -> toVehicle(rs));
  }

  private Vehicle toVehicle(ResultSet rs) throws SQLException {
    return new Vehicle(
        rs.getString("vehicle_no"), //
        rs.getString("color"),
        rs.getInt("wheel"),
        rs.getInt("seat"));
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
