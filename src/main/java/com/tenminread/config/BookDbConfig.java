package com.tenminread.config; // (패키지 위치는 예시입니다)

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
  basePackages = "com.tenminread.bookdb.repository",
  entityManagerFactoryRef = "bookDbEntityManagerFactory",
  transactionManagerRef = "bookDbTransactionManager"
)
public class BookDbConfig {

  @Bean(name = "bookDbDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.bookdb")
  public DataSource bookDbDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "bookDbEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean bookDbEntityManagerFactory(
    EntityManagerFactoryBuilder builder,
    @Qualifier("bookDbDataSource") DataSource dataSource
  ) {
    return builder
      .dataSource(dataSource)
      .packages("com.tenminread.bookdb.domain")
      .persistenceUnit("bookdb")
      .build();
  }

  @Bean(name = "bookDbTransactionManager")
  public PlatformTransactionManager bookDbTransactionManager(
    @Qualifier("bookDbEntityManagerFactory") LocalContainerEntityManagerFactoryBean bookDbEntityManagerFactory
  ) {
    return new JpaTransactionManager(bookDbEntityManagerFactory.getObject());
  }
}
