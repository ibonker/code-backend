/**
 * 
 */
package com.changan.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.changan.code.entity.TableSeniorColumnPO;

/**
 * @author wenxing
 *
 */
public interface TableSeniorColumnRepository
    extends JpaRepository<TableSeniorColumnPO, String>, JpaSpecificationExecutor<TableSeniorColumnPO> {
  
  /**
   * 根据seniorSlaveId查询 TableSeniorColumnPO
   * 
   * @return
   */
  List<TableSeniorColumnPO> findBySeniorSlaveIdOrderByCreatedAtAsc(String seniorSlaveId);
}
