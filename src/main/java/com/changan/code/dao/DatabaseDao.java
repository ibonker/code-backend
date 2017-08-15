package com.changan.code.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.changan.anywhere.common.persistence.mybatis.annotation.MyBatisDao;
import com.changan.anywhere.common.persistence.mybatis.dao.CrudDao;
import com.changan.code.entity.ColumnPO;
import com.changan.code.entity.TablePO;

/**
 * ClassName: DatabaseDao <br/>
 * date: 2016年4月28日 下午2:46:34 <br/>
 * 
 * @author zengjing
 * @version
 * @since JDK 1.7
 */
@MyBatisDao
public interface DatabaseDao extends CrudDao<ColumnPO> {

  /**
   * 查询表列表
   * 
   * @param genTable
   * @return
   */
  List<TablePO> findTableList(TablePO table);

  /**
   * 根据表名称查找表
   * 
   * @param name
   * @return
   */
  TablePO findTable(@Param("dbName") String dbName, @Param("name") String name);

  /**
   * 获取数据表字段
   * 
   * @param genTable
   * @return
   */
  List<ColumnPO> findTableColumnList(TablePO table);

  /**
   * 获取数据表主键
   * 
   * @param genTable
   * @return
   */
  List<String> findTablePK(TablePO table);

  /**
   * 是否已存在该表
   * 
   * @param tableName
   * @return
   */
  int existTable(@Param("dbName") String dbName, @Param("dataSourceName") String dataSourceName,
      @Param("tableName") String tableName);

}
