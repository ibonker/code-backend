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
import com.changan.code.dto.ResultOfColumnDTO;
import com.changan.code.entity.ColumnPO;
import com.changan.code.entity.DatasourcePO;
import com.changan.code.entity.DictTypePO;
import com.changan.code.entity.DictValuePO;
import com.changan.code.repository.ColumnRepository;
import com.changan.code.repository.DatasourceRepository;
import com.changan.code.repository.TableRepository;
import com.changan.code.service.IColumnService;
import com.changan.code.service.IDictService;
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

  @Autowired
  private IDictService dictService;

  @Autowired
  private DatasourceRepository dataSourceRepo;

  @Autowired
  private TableRepository tableRepo;

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
    // TODO 主键属性，用于JPA代码生成
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

  /**
   * 保存字段和字典配置
   */
  @Override
  @Transactional("jpaTransactionManager")
  public ResultOfColumnDTO saveColumnAndDict(ResultOfColumnDTO column) {
    if (column.getColumn().getDictTypeCode().indexOf(" ") != -1
        || "".equals(column.getColumn().getDictTypeCode())) {
      // 设置dictTypeCode为空
      column.getColumn().setDictTypeCode(null);
      // 设置Code
      column.getDictType().setCode(null);
      // 保存字段
      column.setColumn(columnRepo.save(column.getColumn()));
      // 返回保存信息
      return column;
    }
    // 保存字段
    column.setColumn(columnRepo.save(column.getColumn()));
    if ("".equals(column.getDictType().getId()) || column.getDictType().getId() == null) {
      column.setDictType(dictService.insertDictType(column.getDictType()));
    } else {
      // 保存字典表类型
      column.setDictType(
          dictService.updateDictType(column.getDictType().getId(), column.getDictType()));
    }
    // 返回保存信息
    return column;
  }

  /**
   * 获得数据源并执行数据库操作
   */
  @Override
  public void getDataSource(String tableId, DictTypePO dictType, List<DictValuePO> dictValues) {
    // 获取dataSourceId
    String dataSourceId = tableRepo.findOne(tableId).getDatasourceId();
    // 获取数据源
    DatasourcePO datasource = dataSourceRepo.findOne(dataSourceId);
    // 执行数据库操作
    dictService.saveDictValues(datasource, dictType, dictValues);
  }

  /**
   * 获得数据源并执行查询操作
   * 
   * @param tableId
   * @param code
   */
  @Override
  public List<DictValuePO> findTypeAndValue(String tableId, String code) {
    // 获取dataSourceId
    String dataSourceId = tableRepo.findOne(tableId).getDatasourceId();
    // 获取数据源
    DatasourcePO datasource = dataSourceRepo.findOne(dataSourceId);
    // 执行查询操作
    return dictService.findTypeAndValue(datasource, code);
  }
}
