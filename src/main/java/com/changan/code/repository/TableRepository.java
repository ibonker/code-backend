/**
 * 
 */
package com.changan.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.changan.code.entity.TablePO;

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
  @Query("SELECT t.id as id, t.className as className FROM TablePO t WHERE t.datasourceId = ?1")
  List<Object[]> findClassNameByDatasourceId(String datasourceId);
  
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