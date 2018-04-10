/**
 * 
 */
package org.hotpotmaterial.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.hotpotmaterial.code.entity.TableSeniorSlavePO;

/**
 * @author wenxing
 *
 */
public interface TableSeniorSlaveRepository
    extends JpaRepository<TableSeniorSlavePO, String>, JpaSpecificationExecutor<TableSeniorSlavePO> {
  
  /**
   * 根据seniorId查询 TableSeniorSlavePO
   * 
   * @return
   */
  List<TableSeniorSlavePO> findBySeniorIdOrderByCreatedAtAsc(String seniorId);
  
  /**
   * 根据seniorId数组查询 TableSeniorSlavePO
   * 
   * @return
   */
  List<TableSeniorSlavePO> findBySeniorIdInOrderByCreatedAtAsc(List<String> seniorIds);
}
