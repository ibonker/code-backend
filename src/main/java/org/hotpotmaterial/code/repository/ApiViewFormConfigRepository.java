package org.hotpotmaterial.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.hotpotmaterial.code.entity.ApiViewFormConfigPO;

/**
 * 
 * @author cyj
 *
 */
public interface ApiViewFormConfigRepository
    extends JpaRepository<ApiViewFormConfigPO, String>, JpaSpecificationExecutor<ApiViewFormConfigPO> {
  
  /**
   * 根据TableId查询 ApiViewFormConfigPO
   * 
   * @return
   */
  public List<ApiViewFormConfigPO> findByTableIdAndDelFlag(String tableId, String delFlag);
}
