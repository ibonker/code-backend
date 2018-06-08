/**
 * 
 */
package org.hotpotmaterial.code.config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.hotpotmaterial.anywhere.common.datasource.DynamicDataSource;
import org.hotpotmaterial.anywhere.common.persistence.mybatis.entity.BaseEntity;
import org.hotpotmaterial.code.datasource.DynamicLoadDatasource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wenxing
 *
 */
@Configuration
@EnableTransactionManagement
@Slf4j
@MapperScan(basePackages = {"org.hotpotmaterial.code.dao", "org.hotpotmaterial.code.security.dao"},  sqlSessionFactoryRef = "sqlSessionFactory")
public class DatasourceConfig implements TransactionManagementConfigurer {

  /**
   * 定义主数据源的Bean
   * 
   * @return
   */
  @Bean(name = "masterDataSource")
  @ConfigurationProperties("spring.datasource.druid")
  public DataSource datasource() {
    DruidDataSource dataSource = DruidDataSourceBuilder.create().build();

    try {
      dataSource.setFilters("stat,wall");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return dataSource;
  }

  /**
   * 配置动态数据源
   * 
   * @return
   */
  @Bean(name = "dynamicDataSource")
  public DynamicDataSource dynamicDataSource() {
    // 新建动态数据源
    DynamicDataSource dynamicDataSource = new DynamicDataSource();
    // 将主数据源设置为动态数据源
    dynamicDataSource.setMasterDataSource(datasource());
    // 返回动态数据源
    return dynamicDataSource;
  }
  
  /**
   * 配置数据源连接会话
   * 
   * @return
   * @throws Exception
   */
  @Bean(name = "sqlSessionFactory")
  public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
    // 打印数据源会话开始的初始化信息
    log.info("sqlSessionFactory");
    // 新建mybatis
    SqlSessionFactoryBean sessionBean = new SqlSessionFactoryBean();
    // 给mybatis设置数据源
    sessionBean.setDataSource(dynamicDataSource());
    // 配置typeAliasesPackage
    sessionBean.setTypeAliasesPackage("org.hotpotmaterial.code");
    sessionBean.setTypeAliasesSuperType(BaseEntity.class);
    sessionBean.setConfiguration(new MybatisConfig().mybatisConfig());
    // 配置扫描xml文件所在的路径
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    resolver.getResources("classpath:/mappings/*.xml");
    // 以通配符的方式配置mybatis xml的扫描路径
    sessionBean.setMapperLocations(resolver.getResources("classpath*:/mappings/**/*.xml"));
    // 返回数据库连接会话
    return sessionBean.getObject();

  }

  /**
   * 配置事务管理器
   * 
   * @return
   */
  @Bean(name = "transactionManager")
  public DataSourceTransactionManager transactionManager() {
    // 新建事务管理器
    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
    // 将动态数据源加入事务管理器
    transactionManager.setDataSource(dynamicDataSource());
    // 返回事务管理器
    return transactionManager;
  }

  /**
   * 事务管理器接口实现
   */
  @Bean(name = "mybatisTransactionManager")
  @Override
  @Order(value = 1)
  public PlatformTransactionManager annotationDrivenTransactionManager() {
    // 返回事务管理器
    return transactionManager();
  }
  
  /**
   * 载入动态数据源
   * @return
   */
  @Bean
  public DynamicLoadDatasource loadDatasource() {
    return new DynamicLoadDatasource();
  }

}
