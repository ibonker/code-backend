package org.hotpotmaterial.code.datasource;

import java.util.LinkedHashMap;
import java.util.Map;

import org.hotpotmaterial.anywhere.common.datasource.DBContextHolder;
import org.hotpotmaterial.anywhere.common.datasource.DynamicDataSource;
import org.hotpotmaterial.anywhere.common.springsupport.SpringContextHolder;
import org.hotpotmaterial.code.entity.DatasourcePO;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;

import com.alibaba.druid.pool.DruidDataSource;

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
    dataSourceBuider.addPropertyValue("url", datasource.getDburl());
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
