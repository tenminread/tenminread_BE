package com.tenminread.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

  @Bean(name = "mainFlyway", initMethod = "migrate")
  public Flyway mainFlyway(@Qualifier("mainDataSource") DataSource ds) {
    return Flyway.configure()
      .dataSource(ds)
      .locations("classpath:db/migration/main")
      .baselineOnMigrate(true)
      .outOfOrder(false)
      .failOnMissingLocations(true)
      .table("flyway_schema_history")
      .load();
  }

  @Bean(name = "bookFlyway", initMethod = "migrate")
  public Flyway bookFlyway(@Qualifier("bookDataSource") DataSource ds) {
    return Flyway.configure()
      .dataSource(ds)
      .locations("classpath:db/migration/bookdb")
      .baselineOnMigrate(true)
      .outOfOrder(false)
      .failOnMissingLocations(true)
      .table("flyway_schema_history")
      .load();
  }
}
