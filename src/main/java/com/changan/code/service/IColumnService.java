/**
 * 
 */
package com.changan.code.service;

import java.util.List;

import com.changan.code.entity.ColumnPO;
import com.changan.code.entity.DatasourcePO;

/**
 * @author wenxing
 *
 */
public interface IColumnService {

  /**
   * 主数据库字段信息
   * 
   * @param tableId
   * @return
   */
  public List<ColumnPO> findColumnListFromMasterDatasource(String tableId);

  /**
   * 源数据库字段信息
   * 
   * @param datasource
   * @param tableName
   * @return
   */
  public List<ColumnPO> findColumnListFromOriginalDatasource(DatasourcePO datasource,
      String tableName);

  /**
   * 删除冗余的字段信息
   * 
   * @param columns
   */
  public void deleteNotExistColumns(List<ColumnPO> columns);

  /**
   * 批量保存字段
   * 
   * @param columns
   */
  public List<ColumnPO> saveConfigColumns(List<ColumnPO> columns);
  
  /**
   * 保存字段
   * @param column
   * @return
   */
  public ColumnPO saveConfigColumn(ColumnPO column);

  /**
   * 主数据库和源数据库合并信息
   * 
   * @param table
   * @return
   */
  public List<ColumnPO> findMergedColumnsByTable(List<ColumnPO> masterColumns,
      List<ColumnPO> originColumns);

}
