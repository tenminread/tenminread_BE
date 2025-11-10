package com.tenminread.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;
import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(prefix = "spring.flyway", name = "enabled", havingValue = "true", matchIfMissing = false)
public class FlywayConfig {

  @Bean(name = "mainFlyway", initMethod = "migrate")
  public Flyway mainFlyway(@Qualifier("mainDataSource") DataSource ds) {
    return Flyway.configure()
      .dataSource(ds)
      .locations("classpath:db/migration/main")   // ✅ 슬래시(/) 사용
      .baselineOnMigrate(true)
      .outOfOrder(false)
      .failOnMissingLocations(false)              // ✅ 폴더 비어 있어도 죽지 않게(초기 세팅 편의)
      .table("flyway_schema_history")
      .load();
  }

  @Bean(name = "bookFlyway", initMethod = "migrate")
  public Flyway bookFlyway(@Qualifier("bookDbDataSource") DataSource ds) {
    return Flyway.configure()
      .dataSource(ds)
      .locations("classpath:db/migration/bookdb") // ✅ 'book' 아님, 실제 폴더명에 맞춤
      .baselineOnMigrate(true)
      .outOfOrder(false)
      .failOnMissingLocations(true)
      .table("flyway_schema_history")
      .load();
  }
}
