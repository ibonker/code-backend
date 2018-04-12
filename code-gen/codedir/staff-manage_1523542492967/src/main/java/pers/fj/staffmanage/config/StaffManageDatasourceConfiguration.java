package pers.fj.staffmanage.config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
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

/** 
 * 数据源配置 - StaffManageDatasourceConfiguration 
 *
 * @author Hotpotmaterial-Code2
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"pers.fj.staffmanage.dao", "org.hotpotmaterial.dict.mapper"},
    sqlSessionFactoryRef = "sqlSessionFactory")
public class StaffManageDatasourceConfiguration implements TransactionManagementConfigurer {

  /**
   * 配置数据源
   * 
   * @return
   * @throws Exception
   */
  @Bean(name = "staffManageDataSource")
  @ConfigurationProperties("spring.datasource.druid.staff_manage")
  public DataSource dataSource() {
    DruidDataSource dataSource = DruidDataSourceBuilder.create().build();

    try {
      dataSource.setFilters("stat,wall");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return dataSource;
  }

  
  /**
   * 事务管理器接口实现
   */
  @Bean(name = "staffManageTransactionManager")
  @Override
  @Order(value = 1)
  public PlatformTransactionManager annotationDrivenTransactionManager() {
    // 新建事务管理器
    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
    // 将动态数据源加入事务管理器
    transactionManager.setDataSource(dataSource());
    // 返回事务管理器
    return transactionManager;
  }
}