package org.fp024.study.spring5recipes.springbatch.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@RequiredArgsConstructor
@Configuration
// modular = (기본값)false : 구성이 여러 애플리케이션 컨텍스트로 모듈화되는지 여부를 나타냅니다.
// true인 경우 이 컨텍스트에서 @Bean 작업 정의를 생성해서는 안 되며 오히려
// ApplicationContextFactory를 통해 별도의(자식) 컨텍스트에서 제공해야 합니다.
@EnableBatchProcessing(modular = false)
@ComponentScan("org.fp024.study.spring5recipes.springbatch")
@PropertySource("classpath:database.properties")
public class BatchConfiguration {
  private final Environment env;

  @Bean(destroyMethod = "close")
  public DataSource dataSource() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(env.getProperty("jdbc.driver"));
    hikariConfig.setJdbcUrl(env.getProperty("jdbc.url"));
    hikariConfig.setUsername(env.getProperty("jdbc.username"));
    hikariConfig.setPassword(env.getProperty("jdbc.password"));
    return new HikariDataSource(hikariConfig);
  }

  @Bean
  DataSourceInitializer dataSourceInitializer() {
    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource());
    // 데이터 베이스 채우기
    initializer.setDatabasePopulator(databasePopulator());
    return initializer;
  }

  private DatabasePopulator databasePopulator() {
    ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
    // 쿼리 실행에 에러가 있더라도 진행할지 여부
    databasePopulator.setContinueOnError(true);
    // 실패한 SQL DROP 문을 무시할 수 있음을 나타내는 플래그
    databasePopulator.setIgnoreFailedDrops(true);

    databasePopulator.addScript(
        new ClassPathResource("org/springframework/batch/core/schema-drop-hsqldb.sql"));
    databasePopulator.addScript(
        new ClassPathResource("org/springframework/batch/core/schema-hsqldb.sql"));
    return databasePopulator;
  }
}
