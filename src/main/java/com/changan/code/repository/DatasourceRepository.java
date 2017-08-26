/**
 * 
 */
package com.changan.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.changan.code.entity.DatasourcePO;

/**
 * @author wenxing
 *
 */
public interface DatasourceRepository
    extends JpaRepository<DatasourcePO, String>, JpaSpecificationExecutor<DatasourcePO> {
  
  /**
   * 通过project id查询
   * @param projectId
   * @return
   */
  List<DatasourcePO> findByProjectId(String projectId);
  
  /**
   * 通过prjectId获取数目
   * @param projectId
   * @return
   */
  Long countByProjectId(String projectId);

}
