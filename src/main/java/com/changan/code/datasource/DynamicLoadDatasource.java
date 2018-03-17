package com.changan.code.datasource;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;

import com.alibaba.druid.pool.DruidDataSource;
import com.changan.anywhere.common.springsupport.SpringContextHolder;
import com.changan.anywhere.dal.datasource.DBContextHolder;
import com.changan.anywhere.dal.datasource.DynamicDataSource;
import com.changan.code.common.Constants;
import com.changan.code.entity.DatasourcePO;

/**
 * ClassName: DynamicLoadDatasource <br/>
 * date: 2016年4月26日 上午10:24:06 <br/>
 * 
 * @author zengjing
 * @version
 * @since JDK 1.7
 */
public class DynamicLoadDatasource extends ApplicationObjectSupport {
  
  // 数据源字符串常量
  private static final String DATASOURCE_STR = "datasource-";

  private void loadDatasource(DatasourcePO datasource) {
    initBeanFactory().registerBeanDefinition(DATASOURCE_STR + datasource.getId(),
        initDataSource(datasource).getRawBeanDefinition());

  }

  private DefaultListableBeanFactory initBeanFactory() {
    ConfigurableApplicationContext context =
        (ConfigurableApplicationContext) getApplicationContext();

    return (DefaultListableBeanFactory) context.getBeanFactory();
  }

  private BeanDefinitionBuilder initDataSource(DatasourcePO datasource) {
    BeanDefinitionBuilder dataSourceBuider =
        BeanDefinitionBuilder.genericBeanDefinition(DruidDataSource.class);

    // 设置数据库
    String Dbtype = datasource.getDbtype();
    if(Dbtype.equals(Constants.DATASOURCE_ORACLE)) {
    	dataSourceBuider.addPropertyValue("url", Constants.JDBC_ORACLE_PREFIX + datasource.getDburl() + Constants.JDBC_ORACLE_POSTFIX + datasource.getName());
    }
    else {
    	dataSourceBuider.addPropertyValue("url", Constants.JDBC_MYSQL_PREFIX  + datasource.getDburl() + Constants.JDBC_MYSQL_POSTFIX + datasource.getName() + Constants.JDBC_MYSQL_POSTFIX_UTF8_ENCODING);
    }
    dataSourceBuider.addPropertyValue("driverClassName", datasource.getDbdriver());
    dataSourceBuider.addPropertyValue("username", datasource.getDbuser());
    dataSourceBuider.addPropertyValue("password", datasource.getDbpassword());

    return dataSourceBuider;
  }

  public void addDatasource(DatasourcePO datasource) {
    DynamicDataSource dynamicDatasource = SpringContextHolder.getBean(DynamicDataSource.class);

    loadDatasource(datasource);

    Map<Object, Object> slaveDataSources = new LinkedHashMap<>();
    slaveDataSources.put(DATASOURCE_STR + datasource.getId(),
        SpringContextHolder.getBean(DATASOURCE_STR + datasource.getId()));
    dynamicDatasource.setSlaveDataSources(slaveDataSources);
    dynamicDatasource.afterPropertiesSet();
    DBContextHolder.swithToRead();
  }
}
