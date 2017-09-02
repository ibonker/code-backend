/**
 * 
 */
package com.changan.code.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.changan.anywhere.common.datasource.annotation.ChangeDatasource;
import com.changan.code.common.Constants;
import com.changan.code.dao.DatabaseDao;
import com.changan.code.entity.ColumnPO;
import com.changan.code.entity.DatasourcePO;
import com.changan.code.repository.ColumnRepository;
import com.changan.code.service.IColumnService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author wenxing
 *
 */
@Service
public class ColumnServiceImpl implements IColumnService {

  @Autowired
  private ColumnRepository columnRepo;

  @Autowired
  private DatabaseDao databaseDao;

  @Override
  public List<ColumnPO> findColumnListFromMasterDatasource(String tableId) {
    return columnRepo.findByTableId(tableId);
  }

  @Override
  @ChangeDatasource
  public List<ColumnPO> findColumnListFromOriginalDatasource(DatasourcePO datasource,
      String tableName) {
    // 获取表
    List<ColumnPO> columns = databaseDao.findTableColumnList(datasource.getDbtype(), tableName);
    // 获取主键
    List<String> pks = databaseDao.findTablePK(datasource.getDbtype(), tableName);
    for (ColumnPO column : columns) {
      if (pks.contains(column.getName())) {
        column.setIsPk(Constants.IS_ACTIVE);
      }
    }
    return columns;
  }

  @Override
  @Transactional("jpaTransactionManager")
  public void deleteNotExistColumns(List<ColumnPO> columns) {
    columnRepo.delete(columns);
  }
  
  @Override
  @Transactional("jpaTransactionManager")
  public ColumnPO saveConfigColumn(ColumnPO column) {
    return columnRepo.save(column);
  }

  @Override
  @Transactional("jpaTransactionManager")
  public List<ColumnPO> saveConfigColumns(List<ColumnPO> columns) {
    return columnRepo.save(columns);
  }

  @Override
  public List<ColumnPO> findMergedColumnsByTable(List<ColumnPO> masterColumns,
      List<ColumnPO> originColumns) {
    // TODO　主键属性，用于JPA代码生成
    if (null != masterColumns && !masterColumns.isEmpty()) {
      // 合并配置表字段和数据库表字段
      Map<String, ColumnPO> mastermaps = Maps.newHashMap();
      for (ColumnPO mastercolumn : masterColumns) {
        mastermaps.put(mastercolumn.getName(), mastercolumn);
      }
      // 加入配置
      for (ColumnPO origincolumn : originColumns) {
        origincolumn.javaFieldName().setConfigedProperties(mastermaps.get(origincolumn.getName()))
            .setColumnJavaType();
        if (null != mastermaps.get(origincolumn.getName())) {
          mastermaps.remove(origincolumn.getName());
        }
      }

      // 删除冗余字段
      List<ColumnPO> notExistColumns = Lists.newArrayList();
      for (Entry<String, ColumnPO> entry : mastermaps.entrySet()) {
        notExistColumns.add(entry.getValue());
      }
      this.deleteNotExistColumns(notExistColumns);
    } else {
      // 初始化配置
      for (ColumnPO origincolumn : originColumns) {
        origincolumn.javaFieldName().setColumnJavaType();
      }
    }

    return originColumns;
  }

}
