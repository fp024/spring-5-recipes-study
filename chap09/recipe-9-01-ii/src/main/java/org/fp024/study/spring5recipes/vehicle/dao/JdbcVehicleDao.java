package org.fp024.study.spring5recipes.vehicle.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
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
  private static final String SELECT_ALL_SQL = """
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

  private final DataSource dataSource;

  private final JdbcTemplate jdbcTemplate;

  public JdbcVehicleDao(DataSource dataSource, JdbcTemplate jdbcTemplate) {
    this.dataSource = dataSource;
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void insert(Vehicle vehicle) {
    jdbcTemplate.update(
        conn -> {
          PreparedStatement ps = conn.prepareStatement(INSERT_SQL);
          prepareStatement(ps, vehicle);
          return ps;
        });
  }

  @Override
  public Vehicle findByVehicleNo(String vehicleNo) {
    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(SELECT_ONE_SQL)) {
      ps.setString(1, vehicleNo);

      Vehicle vehicle = null;
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          vehicle = toVehicle(rs);
        }
      }
      return vehicle;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Vehicle> findAll() {
    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
        ResultSet rs = ps.executeQuery()) {

      List<Vehicle> vehicles = new ArrayList<>();
      while (rs.next()) {
        vehicles.add(toVehicle(rs));
      }
      return vehicles;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
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
        conn -> {
          PreparedStatement ps = conn.prepareStatement(UPDATE_SQL);
          prepareStatement(ps, vehicle);
          return ps;
        });
  }

  @Override
  public void delete(Vehicle vehicle) {
    jdbcTemplate.update(
        conn -> {
          PreparedStatement ps = conn.prepareStatement(DELETE_SQL);
          ps.setString(1, vehicle.getVehicleNo());
          return ps;
        });
  }
}
