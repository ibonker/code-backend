/**
 * 
 */
package com.changan.code.service;

import java.util.List;

import com.changan.code.entity.DatasourcePO;

/**
 * @author wenxing
 *
 */
public interface IDatasourceService {
  
  /**
   * 检查数据源是否可用
   * 
   * @param datasource
   * @return
   */
  public boolean checkDatasource(DatasourcePO datasource);
  
  /**
   * 根据project id获取datasource
   * @param projectId
   * @return
   */
  public List<DatasourcePO> findByProjectId(String projectId);
  
  /**
   * 删除数据源列表
   * @param datasources
   */
  public void deleteDatasources(List<DatasourcePO> datasources);
  
  /**
   * 保存数据源列表
   * @param datasources
   */
  public void saveDatasources(List<DatasourcePO> datasources);
  
  /**
   * 根据id获取数据源
   * @param datasourceID
   * @return
   */
  public DatasourcePO findById(String datasourceId);
  
  /**
   * 同步数据库表
   * 
   * @param datasourceId
   */
  public void syncTableFromOriginalDatasource(String datasourceId);
  
  /**
   * 获取项目数据源个数
   * @param id
   * @return
   */
  public Long countByProjectId(String projectId);

}
