/**
 * 
 */
package com.changan.code.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.changan.code.entity.ProjectPO;

/**
 * @author wenxing
 *
 */
public interface ProjectRepository
    extends JpaRepository<ProjectPO, String>, JpaSpecificationExecutor<ProjectPO> {
  
}
