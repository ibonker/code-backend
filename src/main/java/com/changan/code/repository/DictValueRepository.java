package com.changan.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.changan.code.entity.DictValuePO;

public interface DictValueRepository 
    extends JpaRepository<DictValuePO, String>, JpaSpecificationExecutor<DictValuePO>{

  
  /**
   * 查询查询指定dictcode的dictValue
   * @param delFlag
   * @return
   */
  List<DictValuePO> findByDictCodeAndDelFlag(String dictCode, String delFlag);
  
  /**
   * 删除指定dictCode下的所有dictValue
   * @param dictCode
   * @return
   */
  @Transactional
  @Modifying
  @Query(value = "UPDATE DictValuePO u SET u.delFlag = :delFlag WHERE u.dictCode = :dictCode")
  int modifyByDictCode(@Param("dictCode")String dictCode, @Param("delFlag")String delFlag);
  
  /**
   * 根据id查询DictValue
   * @param id
   * @param delFlag
   * @return
   */
  DictValuePO findByIdAndDelFlag(String id, String delFlag);
  
  /**
   * 更新dictCode
   * @param dictCode
   */
  @Transactional
  @Modifying
  @Query(value = "UPDATE DictValuePO u SET u.dictCode = :dictCode WHERE u.dictCode = :dictCode AND u.delFlag = :delFlag")
  void updateByDictCode(@Param("dictCode") String dictCode, @Param("delFlag") String delFlag);
}
