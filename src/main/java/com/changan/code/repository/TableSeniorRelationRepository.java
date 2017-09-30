/**
 * 
 */
package com.changan.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.changan.code.entity.TableSeniorRelationPO;

/**
 * @author wenxing
 *
 */
public interface TableSeniorRelationRepository
    extends JpaRepository<TableSeniorRelationPO, String>, JpaSpecificationExecutor<TableSeniorRelationPO> {
  
  /**
   * 根据masterTableId查询 TableSeniorRelationPO
   * 
   * @return
   */
  List<TableSeniorRelationPO> findByMasterTableIdOrderByCreatedAtAsc(String masterTableId);
}
