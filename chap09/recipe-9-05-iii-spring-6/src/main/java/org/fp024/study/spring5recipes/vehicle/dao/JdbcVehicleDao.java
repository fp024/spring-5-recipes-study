package org.fp024.study.spring5recipes.vehicle.dao;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.fp024.study.spring5recipes.vehicle.domain.Vehicle;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

// ✨ 레시피 주제: NamedParameterJdbcDaoSupport : BeanPropertySqlParameterSource
public class JdbcVehicleDao extends NamedParameterJdbcDaoSupport implements VehicleDao {

  private static final String INSERT_SQL =
      """
      INSERT INTO vehicle (color, wheel, seat, vehicle_no)
      VALUES (:color, :wheel, :seat, :vehicleNo)
      """;
  private static final String UPDATE_SQL =
      """
      UPDATE vehicle
         SET color = :color
           , wheel = :wheel
           , seat = :seat
       WHERE vehicle_no = :vehicleNo
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
       WHERE vehicle_no = ?
      """;

  private final JdbcTemplate jdbcTemplate;

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public JdbcVehicleDao(JdbcTemplate jdbcTemplate) {
    setJdbcTemplate(Objects.requireNonNull(jdbcTemplate));
    this.jdbcTemplate = getJdbcTemplate();
    this.namedParameterJdbcTemplate = getNamedParameterJdbcTemplate();
  }

  @Override
  public void insert(Vehicle vehicle) {
    namedParameterJdbcTemplate.update(INSERT_SQL, new BeanPropertySqlParameterSource(vehicle));
  }

  @Override
  public void insert(Collection<Vehicle> vehicles) {
    SqlParameterSource[] parameterSources =
        vehicles.stream() //
            .map(BeanPropertySqlParameterSource::new)
            .toArray(SqlParameterSource[]::new);
    namedParameterJdbcTemplate.batchUpdate(INSERT_SQL, parameterSources);
  }

  @Override
  public Vehicle findByVehicleNo(String vehicleNo) {
    return jdbcTemplate.queryForObject(
        SELECT_ONE_SQL, BeanPropertyRowMapper.newInstance(Vehicle.class), vehicleNo);
  }

  @Override
  public List<Vehicle> findAll() {
    return jdbcTemplate.query(SELECT_ALL_SQL, BeanPropertyRowMapper.newInstance(Vehicle.class));
  }

  @Override
  public void update(Vehicle vehicle) {
    namedParameterJdbcTemplate.update(UPDATE_SQL, new BeanPropertySqlParameterSource(vehicle));
  }

  @Override
  public void delete(Vehicle vehicle) {
    jdbcTemplate.update(DELETE_SQL, vehicle.getVehicleNo());
  }

  @Override
  public String getColor(String vehicleNo) {
    return jdbcTemplate.queryForObject(SELECT_COLOR_SQL, String.class, vehicleNo);
  }

  // 메서드 반환 타입을 int로 두면 NPE 발생할 수 있다고 IDE 경고나와서 고침 (아마도 Eclipse JDT의 자체 경고 같음)
  // 그런데 count 쿼리라서 무조건 숫자를 반환해서 문제는 없을 텐데... 😅
  @Override
  public Integer countAll() {
    return jdbcTemplate.queryForObject(COUNT_ALL_SQL, Integer.class);
  }
}
