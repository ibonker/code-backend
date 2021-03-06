package org.hotpotmaterial.code.datasource.aspect;

import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.hotpotmaterial.anywhere.common.datasource.DBContextHolder;
import org.hotpotmaterial.anywhere.common.springsupport.SpringContextHolder;
import org.hotpotmaterial.code.datasource.DynamicLoadDatasource;
import org.hotpotmaterial.code.entity.DatasourcePO;

/**
 * ClassName: DatasourceChangeAspect <br/>
 * ADD FUNCTION. <br/>
 * ADD REASON(可选). <br/>
 * date: 2016年4月21日 上午9:44:29 <br/>
 * 
 * @author zengjing
 * @version
 * @since JDK 1.7
 */
@Aspect
@Component("codeDataSourceChangeAspect")
@Order(value = 1)
public class DatasourceChangeAspect {

  private Logger log = LoggerFactory.getLogger(DatasourceChangeAspect.class);
  
  private Lock lock = new ReentrantLock();
  /**
   * @Description 定义切点,扫描@ChangeDatasource的方法织入切面
   */
  @Pointcut("@annotation(org.hotpotmaterial.anywhere.common.datasource.annotation.ChangeDatasource)")
  public void changeDatasource() {
    log.info("======================数据源切换======================");
  }

  /**
   * @Description 定义前置通知,操作数据库前配置切换数据源
   * 
   * @param joinPoint
   * @throws SQLException
   */
  @Before("changeDatasource()")
  public void before(JoinPoint joinPoint) {

    // 通过切点获取切点方法中的DatasourceDto参数
    DatasourcePO datasource = (DatasourcePO) joinPoint.getArgs()[0];
    // 动态配置加载数据源
    lock.lock();
    try {
      connectDB(datasource);
    } catch (Exception e) {
      log.error(e.getMessage());
      this.afterChangeDb();
    }
  }

  /**
   * @Description 定义返回通知,方法执行返回后将数据源切回默认数据源
   */
  @AfterReturning("changeDatasource()")
  public void afterChangeDb() {
    // 切换回默认数据源
    DBContextHolder.clear();
    lock.unlock();
  }
  
  @AfterThrowing(pointcut="changeDatasource()", throwing="e")
  public void handleThrowing(Exception e){
    log.error(e.getMessage());
    this.afterChangeDb();
  }

  /**
   * @throws SQLException
   * @Description 动态连接数据源
   */
  private void connectDB(DatasourcePO datasource) throws SQLException {

    DynamicLoadDatasource loadDatasource = SpringContextHolder.getBean(DynamicLoadDatasource.class);
      
    loadDatasource.addDatasource(datasource);
  }
}
