package com.tenminread.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

  // MAIN DB용 Flyway
  @Bean(name = "mainFlyway", initMethod = "migrate")
  public Flyway mainFlyway(@Qualifier("mainDataSource") DataSource ds) {
    return Flyway.configure()
      .dataSource(ds)
      .locations("classpath:db/migration/main")
      .baselineOnMigrate(true)      // 기존 DB에도 처음부터 적용 가능
      .outOfOrder(false)
      .failOnMissingLocations(true)
      .table("flyway_schema_history") // 기본값; 필요시 변경
      .load();
  }

  // BOOK DB용 Flyway
  @Bean(name = "bookFlyway", initMethod = "migrate")
  public Flyway bookFlyway(@Qualifier("bookDataSource") DataSource ds) {
    return Flyway.configure()
      .dataSource(ds)
      .locations("classpath:db/migration/book")
      .baselineOnMigrate(true)
      .outOfOrder(false)
      .failOnMissingLocations(true)
      .table("flyway_schema_history") // DB별로 같은 이름 사용해도 무방(물리 DB가 다름)
      .load();
  }
}
