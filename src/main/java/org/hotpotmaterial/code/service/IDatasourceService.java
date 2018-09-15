/**
 * 
 */
package org.hotpotmaterial.code.service;

import java.util.List;
import java.util.Map;

import org.hotpotmaterial.code.entity.DatasourcePO;
import org.hotpotmaterial.code.entity.TablePO;

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
  public void syncTableFromOriginalDatasource(String datasourceId, String usercode);
  
  /**
   * 获取项目数据源个数
   * @param id
   * @return
   */
  public Long countByProjectId(String projectId);

  /**
   * 获取表名以及描述
   * @param datasource
   * @return
   */
  public Map<String,String> getTableNames(DatasourcePO datasource);

}
