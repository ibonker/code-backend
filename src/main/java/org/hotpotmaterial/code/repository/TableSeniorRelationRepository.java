/**
 * 
 */
package org.hotpotmaterial.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.hotpotmaterial.code.entity.TableSeniorRelationPO;

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
  
  Long deleteByMasterTableId(String masterTableId);
}
