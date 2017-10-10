/**
 * 
 */
package com.changan.code.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.changan.anywhere.common.mvc.page.rest.request.PageDTO;
import com.changan.code.dto.RequestOfTableIdsDTO;
import com.changan.code.dto.SimpleDataObj;
import com.changan.code.entity.ColumnPO;
import com.changan.code.entity.DatasourcePO;
import com.changan.code.entity.ProjectPO;
import com.changan.code.entity.TablePO;
import com.changan.code.entity.TableRelationPO;
import com.changan.code.entity.TableSeniorRelationPO;
import com.changan.code.entity.TransferObjPO;

/**
 * @author wenxing
 *
 */
public interface ITableService {
  
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
  public void activeIsAutoCrud(RequestOfTableIdsDTO tableIds);
  
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
   * 实体文件生成
   * @param projectId
   * @return
   */
  public String generateEntityCodeFiles(String tableId);
  
  /**
   * 下载实体文件
   * @param projectName
   * @return
   */
  public File downLoadFile(String tableName);
  
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
   * 创建dictType
   * @param datasource
   */
  public void creatDictType(DatasourcePO datasource);
  
  /**
   * 创建dictValue
   * @param datasource
   */
  public void creatDictValue(DatasourcePO datasource);
}
