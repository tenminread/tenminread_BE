// src/main/java/com/tenminread/config/MainDbConfig.java
package com.tenminread.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import javax.sql.DataSource;
import java.util.regex.Pattern;

@Profile("!test")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  // ✅ 메인 리포지토리 루트로 변경
  basePackages = "com.tenminread.repository",
  // ✅ 북 DB 리포지토리 제외(충돌/중복 스캔 방지)
  excludeFilters = @ComponentScan.Filter(
    type = FilterType.REGEX,
    pattern = "com\\.tenminread\\.bookdb\\.repository\\..*"
  ),
  entityManagerFactoryRef = "mainEntityManagerFactory",
  transactionManagerRef = "mainTransactionManager"
)
public class MainDbConfig {

  @Primary
  @Bean(name = "mainDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.main")
  public HikariDataSource mainDataSource() {
    return DataSourceBuilder.create()
      .type(HikariDataSource.class)
      .build();
  }

  @Primary
  @Bean(name = "mainEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(
    EntityManagerFactoryBuilder builder,
    @Qualifier("mainDataSource") DataSource dataSource
  ) {
    return builder
      .dataSource(dataSource)
      .packages("com.tenminread.domain") // 메인 엔티티들
      .persistenceUnit("main")
      .build();
  }

  @Primary
  @Bean(name = "mainTransactionManager")
  public PlatformTransactionManager mainTransactionManager(
    @Qualifier("mainEntityManagerFactory") LocalContainerEntityManagerFactoryBean emf
  ) {
    return new JpaTransactionManager(emf.getObject());
  }
}
