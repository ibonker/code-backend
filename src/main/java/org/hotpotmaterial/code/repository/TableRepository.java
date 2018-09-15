/**
 * 
 */
package org.hotpotmaterial.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.hotpotmaterial.code.entity.TablePO;

/**
 * @author wenxing
 *
 */
public interface TableRepository
    extends JpaRepository<TablePO, String>, JpaSpecificationExecutor<TablePO> {
  
  /**
   * 根据datasource id获取table
   * @param datasourceId
   * @return
   */
  List<TablePO> findByDatasourceId(String datasourceId);
  
  /**
   * 获取类名
   * @param datasourceId
   * @return
   */
  @Query("SELECT t.id as id, t.className as className, t.comments as comments FROM TablePO t WHERE t.datasourceId = ?1")
  List<Object[]> findClassNameByDatasourceId(String datasourceId);
  
  /**
   * 根据datasourceId列表获取id
   * @param datasourceId
   * @return
   */
  @Query("SELECT t.id as id FROM TablePO t WHERE t.datasourceId in ?1")
  List<String> findIdByDatasourceIdIn(List<String> datasourceIds);
  
  /**
   * 根据datasourceId列表删除数据
   * @param transferObjIds
   * @return
   */
  Long deleteByDatasourceIdIn(List<String> datasourceIds);
  
  /**
   * 通过id列表获取table列表
   * @param ids
   * @return
   */
  List<TablePO> findByIdIn(List<String> ids);
  
  /**
   * 通过datasourceId,isAutoCrud获取CRUD列表
   * @param datasourceId
   * @param isAutoCrud
   * @return
   */
  List<TablePO> findByDatasourceIdAndIsAutoCrudAndDelFlag(String datasourceId, String isAutoCrud, String delFlag);
  
  /**
   * 根据关联表ID获取table
   * @param datasourceId
   * @return
   */
  @Query("select t from TablePO t where id in (select tr.slaveTableId from TableRelationPO tr where tr.masterTableId = ?1)")
  List<TablePO> findByRelationTableId(String tableId);
  
  /**
   * 根据数据库与表名找到对应的table
   * @param dataSourceId
   * @param tableName
   * @return
   */
  @Query("select t from TablePO t where t.datasourceId = ?1 and t.name = ?2 order by t.createdAt desc")
  List<TablePO> findByDatasourceAndTableName(String dataSourceId,String tableName);
  
  /**
   * 根据数据源ID以及数据库表名查询对应的TablePO的id
   * @param datasourceId  数据源ID
   * @param tableNames  数据库表名
   * @author liujialin 
   */
  @Transactional
  @Modifying
  @Query("select t.id from TablePO t where t.datasourceId = ?1 and t.name IN ?2")
  List<String> selectIsAutoCrud(String datasourceId, List<String> tableNames);

}
