/**
 * 
 */
package org.hotpotmaterial.code.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hotpotmaterial.anywhere.common.persistence.auditing.AuditingDateTimeProvider;
import org.hotpotmaterial.anywhere.common.persistence.auditing.DateTimeService;
import org.hotpotmaterial.anywhere.common.persistence.auditing.SimpleDateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.config.SortedResourcesFactoryBean;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
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
    runSchemaScripts(datasource);
  }

  private void runSchemaScripts(DataSource datasource) {
    List<Resource> scripts =
        getScripts("spring.datasource.schema", this.properties.getSchema(), "schema");
    if (!scripts.isEmpty()) {
      runScripts(scripts, datasource);
    }
  }
  
  private void runScripts(List<Resource> resources, DataSource datasource) {
    if (resources.isEmpty()) {
        return;
    }
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.setContinueOnError(this.properties.isContinueOnError());
    populator.setSeparator(this.properties.getSeparator());
    if (this.properties.getSqlScriptEncoding() != null) {
        populator.setSqlScriptEncoding(this.properties.getSqlScriptEncoding().name());
    }
    for (Resource resource : resources) {
        populator.addScript(resource);
    }
    DatabasePopulatorUtils.execute(populator, datasource);
}

  private List<Resource> getScripts(String propertyName, List<String> resources, String fallback) {
    if (resources != null) {
      return getResources(propertyName, resources, true);
    }
    String platform = this.properties.getPlatform();
    List<String> fallbackResources = new ArrayList<String>();
    fallbackResources.add("classpath*:" + fallback + "-" + platform + ".sql");
    fallbackResources.add("classpath*:" + fallback + ".sql");
    return getResources(propertyName, fallbackResources, false);
  }

  private List<Resource> getResources(String propertyName, List<String> locations,
      boolean validate) {
    List<Resource> resources = new ArrayList<Resource>();
    for (String location : locations) {
      for (Resource resource : doGetResources(location)) {
        if (resource.exists()) {
          resources.add(resource);
        } else if (validate) {
          throw new ResourceNotFoundException(propertyName, resource);
        }
      }
    }
    return resources;
  }

  private Resource[] doGetResources(String location) {
    try {
      SortedResourcesFactoryBean factory = new SortedResourcesFactoryBean(this.applicationContext,
          Collections.singletonList(location));
      factory.afterPropertiesSet();
      return factory.getObject();
    } catch (Exception ex) {
      throw new IllegalStateException("Unable to load resources from " + location, ex);
    }
  }

}
