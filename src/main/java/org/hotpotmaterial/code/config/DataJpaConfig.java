/**
 * 
 */
package org.hotpotmaterial.code.config;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hotpotmaterial.anywhere.common.persistence.auditing.AuditingDateTimeProvider;
import org.hotpotmaterial.anywhere.common.persistence.auditing.DateTimeService;
import org.hotpotmaterial.anywhere.common.persistence.auditing.SimpleDateTimeService;
import org.hotpotmaterial.code.utils.DatasourceInitUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author wenxing
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = {"org.hotpotmaterial.code.repository"},
    entityManagerFactoryRef = "jpaEntityManagerFactory",
    transactionManagerRef = "jpaTransactionManager")
@EnableJpaAuditing(dateTimeProviderRef = "dateTimesProvider")
@EnableConfigurationProperties(JpaProperties.class)
public class DataJpaConfig {

  @Autowired
  private JpaProperties jpaProperties;

  @Autowired
  private DataSourceProperties properties;

  @Autowired
  private ApplicationContext applicationContext;

  /**
   * 配置生成EntityManagerFactoryBuilder的bean
   * 
   * @return
   */
  @Bean(name = "jpaEntityManagerFactoryBuilder")
  public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setShowSql(jpaProperties.isShowSql());
    EntityManagerFactoryBuilder builder =
        new EntityManagerFactoryBuilder(vendorAdapter, jpaProperties.getProperties(), null);
    return builder;
  }

  /**
   * 配置生成EntityManagerFactory的bean
   * 
   * @param builder
   * @return
   */
  @Autowired
  @Bean(name = "jpaEntityManagerFactory")
  public EntityManagerFactory entityManagerFactory(
      @Qualifier("jpaEntityManagerFactoryBuilder") EntityManagerFactoryBuilder builder,
      @Qualifier("masterDataSource") DataSource datasource) {
    LocalContainerEntityManagerFactoryBean factory =
        builder.dataSource(datasource).packages("org.hotpotmaterial.code.entity").build();
    factory.afterPropertiesSet();
    return factory.getObject();
  }

  /**
   * jpa用事务管理bean
   * 
   * @param emFactory
   * @return
   */
  @Autowired
  @Bean(name = "jpaTransactionManager")
  public PlatformTransactionManager transactionManager(
      @Qualifier("jpaEntityManagerFactory") EntityManagerFactory emFactory) {
    JpaTransactionManager txManager = new JpaTransactionManager(emFactory);
    return txManager;
  }

  /**
   * 创建时间和修改时间注解的来源配置
   * 
   * @param dateTimeService
   * @return
   */
  @Bean(name = "dateTimesProvider")
  public AuditingDateTimeProvider dateTimesProvider(DateTimeService dateTimeService) {
    return new AuditingDateTimeProvider(dateTimeService);
  }

  /**
   * 将当时时间服务注册成Bean
   * 
   * @return
   */
  @Bean
  public DateTimeService dateTimeService() {
    return new SimpleDateTimeService();
  }

  /**
   * 初始化schema脚本
   */
  @PostConstruct
  public void initsql() {
    DataSource datasource = (DataSource) this.applicationContext.getBean("masterDataSource");
    DatasourceInitUtils.runSchemaScripts("spring.datasource.schema", properties.getSchema(),
        "schema", datasource, properties, applicationContext);
  }

}
