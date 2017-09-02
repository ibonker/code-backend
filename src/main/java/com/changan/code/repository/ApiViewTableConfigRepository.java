package com.changan.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.changan.code.entity.ApiViewTableConfigPO;

/**
 * 
 * @author cyj
 *
 */
public interface ApiViewTableConfigRepository
    extends JpaRepository<ApiViewTableConfigPO, String>, JpaSpecificationExecutor<ApiViewTableConfigPO> {
  
  /**
   * 根据TableId查询 ApiViewTableConfigPO
   * 
   * @return
   */
  public List<ApiViewTableConfigPO> findByTableIdAndDelFlag(String tableId, String delFlag);
}
