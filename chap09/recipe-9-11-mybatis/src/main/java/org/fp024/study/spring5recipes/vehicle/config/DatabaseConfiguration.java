package org.fp024.study.spring5recipes.vehicle.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.nio.charset.StandardCharsets;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@MapperScan(basePackages = "org.fp024.study.spring5recipes.vehicle.mapper")
abstract class DatabaseConfiguration {

  protected abstract Environment getEnv();

  @Bean
  SqlSessionFactory sqlSessionFactory() throws Exception {
    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    factoryBean.setDataSource(dataSource());
    factoryBean.setMapperLocations(new ClassPathResource("mapper/VehicleMapper.xml"));
    factoryBean.setTypeAliasesPackage("org.fp024.study.spring5recipes.vehicle.domain");
    return factoryBean.getObject();
  }

  @Bean(destroyMethod = "close")
  HikariDataSource dataSource() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(getEnv().getProperty("jdbc.driver"));
    hikariConfig.setJdbcUrl(getEnv().getProperty("jdbc.url"));
    hikariConfig.setUsername(getEnv().getProperty("jdbc.username"));
    hikariConfig.setPassword(getEnv().getProperty("jdbc.password"));
    hikariConfig.setMinimumIdle(2);
    hikariConfig.setMaximumPoolSize(5);
    return new HikariDataSource(hikariConfig);
  }

  @Bean
  ResourceDatabasePopulator databasePopulator() {
    ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
    databasePopulator.setSqlScriptEncoding(StandardCharsets.UTF_8.name());
    databasePopulator.setContinueOnError(false);
    databasePopulator.addScript(
        new ClassPathResource(
            "sql/%s/schema.sql".formatted(getEnv().getProperty("jdbc.database.type"))));

    return databasePopulator;
  }
}
