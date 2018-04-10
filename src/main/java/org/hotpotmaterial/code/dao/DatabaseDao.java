package org.hotpotmaterial.code.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import org.hotpotmaterial.anywhere.common.persistence.mybatis.annotation.MyBatisDao;
import org.hotpotmaterial.anywhere.common.persistence.mybatis.dao.CrudDao;
import org.hotpotmaterial.code.entity.ColumnPO;
import org.hotpotmaterial.code.entity.DictTypePO;
import org.hotpotmaterial.code.entity.DictValuePO;
import org.hotpotmaterial.code.entity.TablePO;

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
  List<TablePO> findTableList(@Param("dbName") String dbName, @Param("name") String name);

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
  List<ColumnPO> findTableColumnList(@Param("dbName") String dbName, @Param("name") String name);

  /**
   * 获取数据表主键
   * 
   * @param genTable
   * @return
   */
  List<String> findTablePK(@Param("dbName") String dbName, @Param("name") String name);

  /**
   * 是否已存在该表
   * 
   * @param tableName
   * @return
   */
  int existTable(@Param("dbName") String dbName, @Param("dataSourceName") String dataSourceName,
      @Param("tableName") String tableName);

  /**
   * 查询dictCode下的value
   * @param dictCode
   * @return
   */
  List<DictValuePO> findDictValueByCode(@Param("dictCode") String dictCode, @Param("delFlag") String delFlag);
  
  /**
   * 保存dictValue
   * @param dictCode
   * @param dictValue
   */
  void saveDictValue(@Param("dictValue")DictValuePO dictValue); 
  
  /**
   * 更新dictValue
   * @param dictValue
   */
  void updateDictValue(@Param("dictValue")DictValuePO dictValue);
  
  /**
   * 根据id删除dictValue
   * @param id
   * @param delFlag
   */
  void deleteDictValue(@Param("id") String id, @Param("delFlag") String delFlag);
  
  /**
   * 删除指定code下的dictValue
   * @param dictCode
   * @param delFlag
   */
  void deleteDictValues(@Param("dictCode") String dictCode, @Param("delFlag") String delFlag);
  
  /**
   * 查询是否存在dictType
   * @return
   */
  int findDictTypeByCode(@Param("code") String code, @Param("delFlag") String delFlag);
  
  /**
   * 新增dictType
   */
  void insertDictType(@Param("dictType")DictTypePO dictType);
  
  /**
   * 查询所有dictType
   * @return
   */
  List<DictTypePO> findDictTypes(@Param("delFlag") String delFlag);
  
  /**
   * 创建dictType表
   */
  void creatDictType(@Param("dbName") String dbName);
  
  /**
   * 创建dictValue表
   */
  void creatDictValue(@Param("dbName") String dbName);
  
  /**
   * 创建uiconfig表
   */
  void createUiConfig(@Param("dbName") String dbName);
}
