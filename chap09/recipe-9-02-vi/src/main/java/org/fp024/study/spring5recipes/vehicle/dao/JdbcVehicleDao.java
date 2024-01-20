package org.fp024.study.spring5recipes.vehicle.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.fp024.study.spring5recipes.vehicle.domain.Vehicle;
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

  private static final String COUNT_ALL_SQL =
      """
      SELECT COUNT(*)
        FROM vehicle
      """;
  private static final String SELECT_COLOR_SQL =
      """
      SELECT color
        FROM vehicle
       WHERE vehicle_no=?
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
    return jdbcTemplate.queryForObject(
        SELECT_ONE_SQL, BeanPropertyRowMapper.newInstance(Vehicle.class), vehicleNo);
  }

  @Override
  public List<Vehicle> findAll() {
    // ✨ 레시피 주제
    List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_ALL_SQL);
    return rows.stream()
        .map(
            row -> {
              Vehicle vehicle = new Vehicle();
              vehicle.setVehicleNo((String) row.get("vehicle_no"));
              vehicle.setColor((String) row.get("color"));
              vehicle.setWheel((Integer) row.get("wheel"));
              vehicle.setSeat((Integer) row.get("seat"));
              return vehicle;
            })
        .toList();
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

  @Override
  public String getColor(String vehicleNo) {
    return jdbcTemplate.queryForObject(SELECT_COLOR_SQL, String.class, vehicleNo);
  }

  // 메서드 반환 타입을 int로 두면 NPE 발생할 수 있다고 IDE 경고나와서 고침
  // 그런데 count 쿼리라서 무조건 숫자를 반환해서 문제는 없을 텐데... 😅
  @Override
  public Integer countAll() {
    return jdbcTemplate.queryForObject(COUNT_ALL_SQL, Integer.class);
  }
}
