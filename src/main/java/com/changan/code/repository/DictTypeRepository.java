package com.changan.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.changan.code.entity.DictTypePO;

public interface DictTypeRepository 
    extends JpaRepository<DictTypePO, String>, JpaSpecificationExecutor<DictTypePO> {

  /**
   * 查询所有的DictType
   * @param delFlag
   * @return
   */
  List<DictTypePO> findByDelFlag(String delFlag);
  
  /**
   * 根据id查询DictType
   * @param id
   * @param delFlag
   * @return
   */
  DictTypePO findByIdAndDelFlag(String id, String delFlag);

  /**
   * 根据code查询DictType
   * @param code
   * @param delFlag
   * @return
   */
  DictTypePO findByCodeAndDelFlag(String code, String delFlag);
}
