package com.changan.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.changan.code.entity.ApiObjPO;

public interface ApiObjRepository
    extends JpaRepository<ApiObjPO, String>, JpaSpecificationExecutor<ApiObjPO> {

  /**
   * 查询apiObj是否存在重复的uri
   * 
   * @param apiBaseId
   * @param uri
   * @return
   */
  public int countByApiBaseIdAndUriAndDelFlag(String apiBaseId, String uri, String delFlag);

  /**
   * 根据ApiBaseId查询 ApiObj
   * 
   * @return
   */
  public List<ApiObjPO> findByApiBaseIdAndDelFlag(String apiBaseId, String delFlag);

  /**
   * 根据id查询ApiObj
   * 
   * @param id
   * @return
   */
  public ApiObjPO findByIdAndDelFlag(String id, String delFlag);
  
  /**
   * 根据genBasedTableId获取数据id
   * 
   * @param genBasedTableId
   * @return
   */
  @Query("SELECT t.id as id FROM ApiObjPO t WHERE t.genBasedTableId = ?1")
  List<String> findIdByGenBasedTableId(String genBasedTableId);
  
  /**
   * 根据genBasedTableId删除数据
   * 
   * @param genBasedTableId
   * @return
   */
  Long deleteByGenBasedTableId(String genBasedTableId);
}
