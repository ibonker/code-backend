package org.hotpotmaterial.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.hotpotmaterial.code.entity.TableRelationPO;

/**
 * 
 * @author cyj
 *
 */
public interface TableRelationRepository
    extends JpaRepository<TableRelationPO, String>, JpaSpecificationExecutor<TableRelationPO> {
  
  /**
   * 根据masterTableId查询 TableRelationPO
   * 
   * @return
   */
  List<TableRelationPO> findByMasterTableIdOrderByCreatedAtAsc(String masterTableId);
  
  /**
   * 根据slaveTableId查询 TableRelationPO
   * 
   * @return
   */
  List<TableRelationPO> findBySlaveTableIdOrderByCreatedAtAsc(String slaveTableId);
  
  /**
   * 根据masterTableId和slaveTableId删除TableRelationPO
   * @param masterTableId
   * @param slaveTableId
   * @return
   */
  Long deleteByMasterTableIdAndSlaveTableId(String masterTableId, String slaveTableId);
  
  /**
   * 根据masterTableId删除
   * @param masterTableId
   * @return
   */
  Long deleteByMasterTableId(String masterTableId);
  
  /**
   * 根据SlaveTableId删除
   * @param masterTableId
   * @return
   */
  Long deleteBySlaveTableId(String slaveTableId);
}
