package org.hotpotmaterial.code.datasource;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.hotpotmaterial.anywhere.common.datasource.DynamicDataSource;
import org.hotpotmaterial.anywhere.common.springsupport.SpringContextHolder;
import org.hotpotmaterial.code.config.DatasourceConfig;

/**
 * ClassName: BeanInitializer <br/>
 * Function: 监听bean的注册和刷新事件,进行加载. <br/>
 * Reason: ADD REASON(可选). <br/>
 * date: 2016年4月25日 下午5:24:38 <br/>
 * 
 * @author zengjing
 * @version
 * @since JDK 1.7
 */
public class DynamicDatasourceListener implements ApplicationListener<ApplicationEvent> {

  /**
   * 打印日志
   */
  private static Logger log = LoggerFactory.getLogger(DatasourceConfig.class);

  /**
   * 监听Spring容器中的数据源重载
   */
  @Override
  public void onApplicationEvent(ApplicationEvent event) {
    if (event instanceof ContextRefreshedEvent) {
      try {
        getConnection();
      } catch (SQLException e) {
        log.error("重载数据源出现异常!!!", e);
      }
    }
  }

  /**
   * 连接数据源并打印日志
   * 
   * @throws SQLException
   */
  private void getConnection() throws SQLException {
    log.info("======================数据源重载======================");
    SpringContextHolder.getBean(DynamicDataSource.class).getConnection();
  }
}
