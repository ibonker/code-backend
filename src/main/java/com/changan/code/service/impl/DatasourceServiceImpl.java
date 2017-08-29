/**
 * 
 */
package com.changan.code.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.druid.pool.DruidDataSource;
import com.changan.code.common.Constants;
import com.changan.code.entity.DatasourcePO;
import com.changan.code.entity.TablePO;
import com.changan.code.repository.DatasourceRepository;
import com.changan.code.service.IDatasourceService;
import com.changan.code.service.ITableService;

/**
 * @author wenxing
 *
 */
@Service
public class DatasourceServiceImpl implements IDatasourceService {

  // 注入datasource repository
  @Autowired
  private DatasourceRepository datasourceRepo;
  // 注入表service
  @Autowired
  private ITableService tableService;

  /**
   * 检查数据源是否可用
   * 
   * @param datasource
   * @return
   */
  @Override
  public boolean checkDatasource(DatasourcePO datasource) {
    try {
      // 新建数据源
      DruidDataSource dataSource = new DruidDataSource();
      // 设置数据源驱动名称
      dataSource.setDriverClassName(datasource.getDbdriver());
      // 设置数据源用户名
      dataSource.setUsername(datasource.getDbuser());
      // 设置数据源密码
      dataSource.setPassword(datasource.getDbpassword());
      // 设置数据源连接字符串
      dataSource.setUrl(datasource.getDburl());
      // 数据源连接的测试的其它设置
      dataSource.setInitialSize(5);
      dataSource.setMinIdle(1);
      // 新建JDBC对象
      JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
      // 执行SQL
      jdbcTemplate.execute("select 1 from dual");
      // 关闭数据源
      dataSource.close();
      // 当数据源测试成功返回true
      return true;

    } catch (Exception e) {
      // 当数据源测试出现异常返回false
      return false;
    }
  }

  @Override
  public List<DatasourcePO> findByProjectId(String projectId) {
    return datasourceRepo.findByProjectId(projectId);
  }

  @Override
  public void deleteDatasources(List<DatasourcePO> datasources) {
    datasourceRepo.delete(datasources);
  }

  @Override
  public void saveDatasources(List<DatasourcePO> datasources) {
    for (DatasourcePO datasource : datasources) {
      // 设置对应包名
      datasource.setPackageName(datasource.getName().toLowerCase().replace("_", "."));
      // 设置driver
      datasource.setDbdriver(Constants.DATASOURCE_MYSQL.equals(datasource.getDbtype())
          ? Constants.DATASOURCE_MYSQL_DRIVER : Constants.DATASOURCE_ORACLE_DRIVER);
    }
    datasourceRepo.save(datasources);
  }

  @Override
  public DatasourcePO findById(String datasourceId) {
    return datasourceRepo.findOne(datasourceId);
  }

  @Override
  public void syncTableFromOriginalDatasource(String datasourceId) {
    // 获取datasource
    DatasourcePO datasource = this.findById(datasourceId);
    // 获取数据源的表
    List<TablePO> originTables = tableService.findTableListFromOriginalDatasource(datasource);
    // 获取保存的表
    List<TablePO> masterTables = tableService.findTableListFromMasterDatasource(datasourceId);
    // 同步数据库表
    tableService.saveAndDelMasterTables(originTables, masterTables, datasourceId);
  }

  @Override
  public Long countByProjectId(String projectId) {
    return datasourceRepo.countByProjectId(projectId);
  }

}