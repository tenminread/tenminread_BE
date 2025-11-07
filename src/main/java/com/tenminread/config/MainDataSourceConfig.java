package com.tenminread.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  basePackages = "com.tenminread.repository.main",
  entityManagerFactoryRef = "mainEntityManagerFactory",
  transactionManagerRef = "mainTransactionManager"
)
public class MainDataSourceConfig {

  @Bean
  @Primary
  @ConfigurationProperties("spring.datasource.main")
  public DataSourceProperties mainDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean(name = "mainDataSource")
  @Primary
  public DataSource mainDataSource() {
    HikariDataSource ds = mainDataSourceProperties().initializeDataSourceBuilder()
      .type(HikariDataSource.class)
      .build();
    return new TransactionAwareDataSourceProxy(ds);
  }

  @Bean(name = "mainEntityManagerFactory")
  @Primary
  public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(
    @Qualifier("mainDataSource") DataSource dataSource
  ) {
    var vendorAdapter = new HibernateJpaVendorAdapter();
    var props = new HashMap<String, Object>();
    // 전역 application.yml의 hibernate 설정 사용 (ddl-auto=validate)

    var emf = new LocalContainerEntityManagerFactoryBean();
    emf.setDataSource(dataSource);
    emf.setPackagesToScan("com.tenminread.domain.main");
    emf.setJpaVendorAdapter(vendorAdapter);
    emf.setJpaPropertyMap(props);
    emf.setPersistenceUnitName("main-pu");
    return emf;
  }

  @Bean(name = "mainTransactionManager")
  @Primary
  public PlatformTransactionManager mainTransactionManager(
    @Qualifier("mainEntityManagerFactory") LocalContainerEntityManagerFactoryBean emf
  ) {
    return new JpaTransactionManager(emf.getObject());
  }

  @Bean
  @Primary
  public PersistenceExceptionTranslationPostProcessor mainExceptionTranslator() {
    return new PersistenceExceptionTranslationPostProcessor();
  }
}
