package org.fp024.study.spring5recipes.vehicle.util;

import javax.sql.DataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

@Component
public class DbResetUtils {
  private final ResourceDatabasePopulator dbPopulator;
  private final DataSource dataSource;

  public DbResetUtils(ResourceDatabasePopulator dbPopulator, DataSource dataSource) {
    this.dbPopulator = dbPopulator;
    this.dataSource = dataSource;
  }

  /** 데이터 베이스 초기화 */
  public void resetDB() {
    dbPopulator.execute(dataSource);
  }
}
