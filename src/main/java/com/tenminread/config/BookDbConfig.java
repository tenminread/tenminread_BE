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

import javax.sql.DataSource;

@Profile("!test")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  basePackages = "com.tenminread.bookdb.repository",
  entityManagerFactoryRef = "bookDbEntityManagerFactory",
  transactionManagerRef = "bookDbTransactionManager"
)
public class BookDbConfig {

  @Bean(name = "bookDbDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.bookdb")
  public HikariDataSource bookDbDataSource() {
    return DataSourceBuilder.create()
      .type(HikariDataSource.class)
      .build();
  }

  @Bean(name = "bookDbEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean bookDbEntityManagerFactory(
    EntityManagerFactoryBuilder builder,
    @Qualifier("bookDbDataSource") DataSource dataSource
  ) {
    return builder
      .dataSource(dataSource)
      .packages("com.tenminread.bookdb.domain") // BookText, SummaryText 등
      .persistenceUnit("bookdb")
      .build();
  }

  @Bean(name = "bookDbTransactionManager")
  public PlatformTransactionManager bookDbTransactionManager(
    @Qualifier("bookDbEntityManagerFactory") LocalContainerEntityManagerFactoryBean emf
  ) {
    return new JpaTransactionManager(emf.getObject());
  }
}
