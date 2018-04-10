/**
 * 
 */
package org.hotpotmaterial.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

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

}
