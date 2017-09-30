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
  @Query("SELECT t.id as id, t.name as name, t.packageName as packageName, t.isGeneric as isGeneric, t.comments as comments FROM TransferObjPO t WHERE t.projectId = ?1 and t.delFlag = '0'")
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
   * 根据genBasedTableId列表获取数据id
   * 
   * @param genBasedTableId
   * @return
   */
  @Query("SELECT t.id as id FROM TransferObjPO t WHERE t.genBasedTableId in ?1")
  List<String> findIdByGenBasedTableIdIn(List<String> genBasedTableIds);
  
  /**
   * 根据genBasedTableId获取数据id
   * 
   * @param genBasedTableId
   * @return
   */
  @Query("SELECT t.id as id FROM TransferObjPO t WHERE t.genBasedTableId = ?1 and t.isSenior = '1'")
  List<String> findSeniorIdByGenBasedTableId(String genBasedTableId);

  /**
   * 根据genBasedTableId获取数据
   * 
   * @param genBasedTableId
   * @return
   */
  List<TransferObjPO> findByGenBasedTableId(String genBasedTableId);

  /**
   * 根据genBasedTableId删除数据
   * 
   * @param genBasedTableId
   * @return
   */
  Long deleteByGenBasedTableId(String genBasedTableId);
  
  /**
   * 根据genBasedTableId列表删除数据
   * 
   * @param genBasedTableId
   * @return
   */
  Long deleteByGenBasedTableIdIn(List<String> genBasedTableId);
  
  /**
   * 根据genBasedTableId和is senior删除数据
   * 
   * @param genBasedTableId
   * @return
   */
  Long deleteByGenBasedTableIdAndIsSenior(String genBasedTableId, String isSenior);

  /**
   * 根据name、projectId查询实体
   * 
   * @param name
   * @param ProjectId
   * @param DelFlag
   * @return
   */
  public List<TransferObjPO> findByNameAndProjectIdAndDelFlag(String name, String projectId,
      String delFlag);

  /**
   * 查询DTO名称重复行数量
   * 
   * @param name
   * @param projectId
   * @return
   */
  public List<TransferObjPO> findByNameAndPackageNameAndProjectIdAndDelFlag(String name,
      String packageName, String projectId, String delFlag);

  /**
   * 根据name、projectId查询实体
   * 
   * @param name
   * @param ProjectId
   * @param DelFlag
   * @return
   */
  public List<TransferObjPO> findByGenBasedTableIdAndIsSeniorAndDelFlag(String genBasedTableId,
      String isSenior, String delFlag);
}
