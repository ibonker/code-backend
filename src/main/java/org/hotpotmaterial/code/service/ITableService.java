/**
 * 
 */
package org.hotpotmaterial.code.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import org.hotpotmaterial.anywhere.common.mvc.page.rest.request.PageDTO;
import org.hotpotmaterial.code.dto.RequestOfTableIdsDTO;
import org.hotpotmaterial.code.dto.SimpleDataObj;
import org.hotpotmaterial.code.entity.ColumnPO;
import org.hotpotmaterial.code.entity.DatasourcePO;
import org.hotpotmaterial.code.entity.ProjectPO;
import org.hotpotmaterial.code.entity.TablePO;
import org.hotpotmaterial.code.entity.TableRelationPO;
import org.hotpotmaterial.code.entity.TableSeniorRelationPO;
import org.hotpotmaterial.code.entity.TransferObjPO;

/**
 * @author wenxing
 *
 */
public interface ITableService {
  
  /**
   * 根据id查找
   * @param id
   * @return
   */
  public TablePO findById(String id);
  
  /**
   * 根据datasource id更新数据库表信息，并返回最新数据库表
   * @param datasource
   * @return
   */
  public List<TablePO> findTableListFromMasterDatasource(String datasourceId);
  
  /**
   * 从原始数据库取值
   * @return
   */
  public List<TablePO> findTableListFromOriginalDatasource(DatasourcePO datasource);
  
  /**
   * 保存新添加表
   * @param tables
   */
  public void saveNewTables(List<TablePO> tables);
  
  /**
   * 更新表
   * @param tables
   */
  public void updateTable(String id, TablePO table);
  
  /**
   * 删除不存在的表
   * @param tables
   */
  public void deleteNotExistTables(List<TablePO> tables);
  
  /**
   * 检查数据源是否可用
   * 
   * @param datasource
   * @return
   */
  public void saveAndDelMasterTables(List<TablePO> originTables, List<TablePO> masterTables,
      DatasourcePO datasource, ProjectPO project);
  
  /**
   * 分页查询table
   * @param searchParams
   * @return
   */
  public Page<TablePO> findTablesPage(String datasourceId, PageDTO searchParams);
  
  /**
   * 获取table的字段
   * @param tableId
   * @return
   */
  public List<ColumnPO> findMergedColumns(String tableId);
  
  /**
   * 获取表的类名
   * @param datasourceId
   * @return
   */
  public List<SimpleDataObj> findClassnameByDatasourceId(String datasourceId);
  
  /**
   * 获取表的类名
   * @param projectId
   * @return
   */
  public Map<String, List<SimpleDataObj>> findClassnameByProjectId(String projectId);
  
  /**
   * 更新自动创建crud状态-active
   * @param ids
   */
  public void activeIsAutoCrud(RequestOfTableIdsDTO tableIds, String usercode);
  
  /**
   * 更新自动创建crud状态-inactive
   * @param ids
   */
  public void inactiveIsAutoCrud(RequestOfTableIdsDTO tableIds);
  
  /**
   * 转换column到trans
   * @return
   */
  public TransferObjPO transColumnPOToTransPO(String tableId);
  
  /**
   * 下载实体文件
   * @param projectName
   * @return
   */
  public String generateTableCodes(String tableId) throws FileNotFoundException;
  
  /**
   * 下载文件
   */
  public File downloadZipFiles(String tableName);
  
  /**
   * 获取表
   * @param datasourceId
   * @return
   */
  public List<TablePO> findByDatasourceId(String datasourceId);
  
  /**
   * 新增restFul关联表
   * @param tableRelation
   */
  public TableRelationPO saveTableRelation(TableRelationPO tableRelation);
  
  /**
   * 删除该表restFul关联关系
   * @param id
   */
  public void deletTableRelation(String id);
  
  /**
   * 查询该表所有restFul关联表
   * @param masterTableId
   * @return
   */
  public List<TableRelationPO> findTableRelationList(String masterTableId);
  
  /**
   * 查询从表表所有restFul关联表
   * @param slaveTableId
   * @return
   */
  public List<TableRelationPO> findSlaveTableRelationList(String slaveTableId);
  
  /**
   * 新增高级关联表
   * @param tableSeniorRelation
   */
  public TableSeniorRelationPO saveTableSeniorRelation(TableSeniorRelationPO tableSeniorRelation);
  
  /**
   * 查询高级关联sql
   * @param masterTableId
   * @return
   */
  public List<TableSeniorRelationPO> findTableSeniorRelationSqlList(String masterTableId);
  
  /**
   * 删除该表高级关联关系
   * @param id
   */
  public void deletTableSeniorRelation(String id);
  
  /**
   * 获取高级关联关系
   * @param id
   * @return
   */
  public TableSeniorRelationPO findOnetableSeniorRelation(String id);

  
  /**
   * 根据datasourceId列表获取id
   * @param datasourceId
   * @return
   */
  public List<String> findIdByDatasourceIdIn(List<String> datasourceIds);
  
  /**
   * 根据datasourceId列表删除数据
   * @param transferObjIds
   * @return
   */
  public Long deleteByDatasourceIdIn(List<String> datasourceIds);
  
  /**
   * 是否启用表字段
   * @param tableId
   * @return
   */
  public Boolean isDictionary(String tableId);
  
  /**
   * 创建uiconfig
   * @param datasource
   */
  public void createUiConfig(DatasourcePO datasource);
  
  /**
   * 创建security相关表
   * @param datasource
   */
  public void createSecurity(DatasourcePO datasource);
  
  /**
   * 创建ressecurity相关表
   * @param datasource
   */
  public void createResSecurity(DatasourcePO datasource, ProjectPO project);

  /**
   * 创建dictType
   * @param datasource
   */
  public void creatDictType(DatasourcePO datasource);
  
  /**
   * 创建dictValue
   * @param datasource
   */
  public void creatDictValue(DatasourcePO datasource);
  
  /**
   * 获取该数据库下被激活的实体以及它的关联实体和组件
   * @param datasourceId
   * @param projectId
   * @return
   */
  public List<TablePO> findByDatasourceId(String datasourceId, String projectId);

  /**
   * 获取激活PO与关联PO对应的Class
   * @param datasourceId
   * @param projectId
   * @return
   */
  List<SimpleDataObj> findClassnameByDatasourceIdAndProjectId(String datasourceId, String projectId);
  
}
