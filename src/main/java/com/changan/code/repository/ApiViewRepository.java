package com.changan.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.changan.code.entity.ApiViewPO;

/**
 * 
 * @author cyj
 *
 */
public interface ApiViewRepository
    extends JpaRepository<ApiViewPO, String>, JpaSpecificationExecutor<ApiViewPO> {
  
  /**
   * 根据ProjectId查询 ApiView
   * 
   * @return
   */
  public List<ApiViewPO> findByProjectIdAndDelFlag(String projectId, String delFlag);
}
