/**
 * 
 */
package org.hotpotmaterial.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.hotpotmaterial.code.entity.ColumnPO;

/**
 * @author wenxing
 *
 */
public interface ColumnRepository
    extends JpaRepository<ColumnPO, String>, JpaSpecificationExecutor<ColumnPO> {
  
  /**
   * 通过table id查询
   * @param tableId
   * @return
   */
  List<ColumnPO> findByTableId(String tableId);

}
