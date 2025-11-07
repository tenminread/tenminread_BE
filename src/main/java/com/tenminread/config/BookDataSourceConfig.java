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

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
  basePackages = "com.tenminread.repository.book",
  entityManagerFactoryRef = "bookEntityManagerFactory",
  transactionManagerRef = "bookTransactionManager"
)
public class BookDataSourceConfig {

  @Bean
  @ConfigurationProperties("spring.datasource.book")
  public DataSourceProperties bookDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean(name = "bookDataSource")
  public DataSource bookDataSource() {
    HikariDataSource ds = bookDataSourceProperties().initializeDataSourceBuilder()
      .type(HikariDataSource.class)
      .build();
    return new TransactionAwareDataSourceProxy(ds);
  }

  @Bean(name = "bookEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean bookEntityManagerFactory(
    @Qualifier("bookDataSource") DataSource dataSource
  ) {
    var vendorAdapter = new HibernateJpaVendorAdapter();
    var props = new HashMap<String, Object>();

    var emf = new LocalContainerEntityManagerFactoryBean();
    emf.setDataSource(dataSource);
    emf.setPackagesToScan("com.tenminread.domain.book");
    emf.setJpaVendorAdapter(vendorAdapter);
    emf.setJpaPropertyMap(props);
    emf.setPersistenceUnitName("book-pu");
    return emf;
  }

  @Bean(name = "bookTransactionManager")
  public PlatformTransactionManager bookTransactionManager(
    @Qualifier("bookEntityManagerFactory") LocalContainerEntityManagerFactoryBean emf
  ) {
    return new JpaTransactionManager(emf.getObject());
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor bookExceptionTranslator() {
    return new PersistenceExceptionTranslationPostProcessor();
  }
}
