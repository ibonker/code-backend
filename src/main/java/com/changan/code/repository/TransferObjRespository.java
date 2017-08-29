package com.changan.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.changan.code.entity.TransferObjPO;

/**
 * 
 * @author xuyufeng
 *
 */
public interface TransferObjRespository
    extends JpaRepository<TransferObjPO, String>, JpaSpecificationExecutor<TransferObjPO> {

  /**
   * 查询DTO名称重复行数量
   * 
   * @param name
   * @param projectId
   * @return
   */
  public int countByNameAndProjectIdAndDelFlag(String name, String projectId, String delFlag);


  /**
   * 获取类名
   * 
   * @param datasourceId
   * @return
   */
  @Query("SELECT t.id as id, t.name as name, t.packageName as packageName FROM TransferObjPO t WHERE t.projectId = ?1")
  List<Object[]> findClassNameByProjectId(String projectId);

  /**
   * 通过id查询TranferObj
   * 
   * @param id
   * @return
   */
  public TransferObjPO findByIdAndDelFlag(String id, String delFlag);


  /**
   * 查询所有TransferObj
   * 
   * @return
   */
  public List<TransferObjPO> findByProjectIdAndDelFlag(String projectId, String delFlag);

  /**
   * 根据genBasedTableId获取数据id
   * 
   * @param genBasedTableId
   * @return
   */
  @Query("SELECT t.id as id FROM TransferObjPO t WHERE t.genBasedTableId = ?1")
  List<String> findIdByGenBasedTableId(String genBasedTableId);

  /**
   * 根据genBasedTableId删除数据
   * 
   * @param genBasedTableId
   * @return
   */
  Long deleteByGenBasedTableId(String genBasedTableId);
}
