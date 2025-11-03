package com.tenminread.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  basePackages = "com.tenminread.repository",
  entityManagerFactoryRef = "mainEntityManagerFactory",
  transactionManagerRef = "mainTransactionManager"
)
public class MainDbConfig {

  @Primary
  @Bean(name = "mainDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.main")
  public DataSource mainDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Primary
  @Bean(name = "mainEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(
    EntityManagerFactoryBuilder builder,
    @Qualifier("mainDataSource") DataSource dataSource
  ) {
    return builder
      .dataSource(dataSource)
      .packages("com.tenminread.domain")
      .persistenceUnit("main")
      .build();
  }

  @Primary
  @Bean(name = "mainTransactionManager")
  public PlatformTransactionManager mainTransactionManager(
    @Qualifier("mainEntityManagerFactory") LocalContainerEntityManagerFactoryBean mainEntityManagerFactory
  ) {
    return new JpaTransactionManager(mainEntityManagerFactory.getObject());
  }
}
